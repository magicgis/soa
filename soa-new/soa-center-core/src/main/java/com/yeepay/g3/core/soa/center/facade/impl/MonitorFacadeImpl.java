/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.monitor.MonitorService;
import com.google.common.collect.Maps;
import com.yeepay.g3.core.soa.center.dao.MonitorDataDao;
import com.yeepay.g3.core.soa.center.entity.MonitorChartData;
import com.yeepay.g3.core.soa.center.entity.MonitorData;
import com.yeepay.g3.core.soa.center.entity.MonitorDataBuilder;
import com.yeepay.g3.core.soa.center.monitor.MonitorDataSaver;
import com.yeepay.g3.core.soa.center.monitor.MonitorDataSaverFactory;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.MonitorChartDataDTO;
import com.yeepay.g3.facade.soa.center.dto.MonitorChartDataDTOWarpper;
import com.yeepay.g3.facade.soa.center.dto.MonitorChartDataItemDTO;
import com.yeepay.g3.facade.soa.center.dto.MonitorDataQueryVO;
import com.yeepay.g3.facade.soa.center.dto.MonitorStatisticsDataDTO;
import com.yeepay.g3.facade.soa.center.dto.MonitorStatisticsDataDTOWarpper;
import com.yeepay.g3.facade.soa.center.facade.MonitorFacade;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.config.ConfigParam;
import com.yeepay.g3.utils.config.ConfigurationUtils;

/**
 * Title: 服务监控 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/>
 * <br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-9 10:47
 */
@Service
public class MonitorFacadeImpl implements MonitorFacade, MonitorService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MonitorFacadeImpl.class);

	private static final String POISON_PROTOCOL = "poison";

	private final Thread writeThread;

	private final BlockingQueue<URL> queue;

	private volatile boolean running = true;

	private static MonitorService INSTANCE = null;

	public static MonitorService getInstance() {
		return INSTANCE;
	}

	@Autowired
	private MonitorDataDao monitorDataDao;

	public MonitorFacadeImpl() {
		queue = new LinkedBlockingQueue<URL>(Integer.parseInt(ConfigUtils
				.getProperty("soa.monitor.queue", "100000")));
		writeThread = new Thread(new Runnable() {
			public void run() {
				while (running) {
					try {
						write(); // 记录统计日志
					} catch (Throwable t) { // 防御性容错
						LOGGER.error(
								"Unexpected error occur at write stat log, cause: "
										+ t.getMessage(), t);
						try {
							Thread.sleep(5000); // 失败延迟
						} catch (Throwable t2) {
						}
					}
				}
			}
		});
		writeThread.setDaemon(true);
		writeThread.setName("DubboMonitorAsyncWriteLogThread");
		writeThread.start();
		INSTANCE = this;
	}

	@PreDestroy
	public void close() {
		try {
			running = false;
			queue.offer(new URL(POISON_PROTOCOL, NetUtils.LOCALHOST, 0));
		} catch (Throwable t) {
			LOGGER.warn(t.getMessage(), t);
		}
	}

	@SuppressWarnings("unchecked")
	private void write() throws Exception {
		URL statistics = queue.take();

		// 避免保存空监控数据
		if (statistics.getParameter(SUCCESS, 0) == 0
				&& statistics.getParameter(FAILURE, 0) == 0) {
			LOGGER.debug("监控数据为空");
			return;
		}

		if (POISON_PROTOCOL.equals(statistics.getProtocol())) {
			return;
		}
		String timestamp = statistics.getParameter(Constants.TIMESTAMP_KEY);
		Date now;
		if (timestamp == null || timestamp.length() == 0) {
			now = new Date();
		} else if (timestamp.length() == 14) {
			now = new SimpleDateFormat("yyyyMMddHHmmss").parse(timestamp);
		} else {
			now = new Date(Long.parseLong(timestamp));
		}
		String day = new SimpleDateFormat("yyyyMMdd").format(now);

		String type;
		String consumer;
		String provider;
		if (statistics.hasParameter(PROVIDER)) {
			type = CONSUMER;
			consumer = statistics.getHost();
			provider = statistics.getParameter(PROVIDER);
			int i = provider.indexOf(':');
			if (i > 0) {
				provider = provider.substring(0, i);
			}
		} else {
			type = PROVIDER;
			consumer = statistics.getParameter(CONSUMER);
			int i = consumer.indexOf(':');
			if (i > 0) {
				consumer = consumer.substring(0, i);
			}
			provider = statistics.getHost();
		}

		try {
			MonitorDataBuilder monitorDataBuilder = new MonitorDataBuilder();
			monitorDataBuilder
					.setDateStr(day)
					.setService(statistics.getServiceInterface())
					.setMethod(statistics.getParameter(METHOD))
					.setConsumer(consumer)
					.setProvider(provider)
					.setType(type)
					.setSuccess(statistics.getParameter(SUCCESS, 0))
					.setFailure(statistics.getParameter(FAILURE, 0))
					.setElapsed(statistics.getParameter(ELAPSED, 0))
					.setConcurrent(statistics.getParameter(CONCURRENT, 0))
					.setMaxElapsed(statistics.getParameter(MAX_ELAPSED, 0))
					.setMaxConcurrent(
							statistics.getParameter(MAX_CONCURRENT, 0));

			MonitorData data = monitorDataBuilder.createMonitorData();
			ConfigParam<?> saversConfig = ConfigurationUtils
					.getSysConfigParam(SoaCenterConst.CONFIG_KEY_SAVER_SWITCH);
			if (saversConfig == null || saversConfig.getValue() == null) {
				// 没有配置开关，直接写数据库
				MonitorDataSaverFactory.getSaver("database").save(data);
			} else {
				Map<String, Boolean> savers = (Map<String, Boolean>) saversConfig
						.getValue();
				for (String name : savers.keySet()) {
					Boolean s = savers.get(name);
					if (s == null || !s) {
						continue;
					}
					MonitorDataSaver saver = MonitorDataSaverFactory
							.getSaver(name);
					if (saver != null) {
						saver.save(data);
					}
				}
			}
		} catch (Throwable t) {
			LOGGER.error("保存监控数据时报错，statistics: " + statistics.toString(), t);
		}
	}

	public void count(URL statistics) {
		collect(statistics);
	}

	public void collect(URL statistics) {
		queue.offer(statistics);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("collect statistics: " + statistics);
		}
	}

	public List<URL> lookup(URL query) {
		// do nothing
		return null;
	}

	@Override
	public Map<String, MonitorStatisticsDataDTOWarpper> lookupStatisticsData(
			MonitorDataQueryVO queryVO) {
		List<MonitorStatisticsDataDTO> statisticsData = monitorDataDao
				.lookupStatisticsData(queryVO);
		Map<String, MonitorStatisticsDataDTOWarpper> warpperMap = Maps
				.newHashMap();
		for (MonitorStatisticsDataDTO item : statisticsData) {
			String key = item.getService() + "|" + item.getMethod();
			MonitorStatisticsDataDTOWarpper warpper = warpperMap.get(key);
			if (null == warpper) {
				warpper = new MonitorStatisticsDataDTOWarpper();
				warpperMap.put(key, warpper);
			}
			warpper.getData().add(item);
		}

		// 求和
		for (Map.Entry<String, MonitorStatisticsDataDTOWarpper> entry : warpperMap
				.entrySet()) {
			Integer success = 0;
			Integer failure = 0;
			Integer elapsed = 0;
			Integer maxElapsed = 0;
			Integer concurrent = 0;
			Integer maxConcurrent = 0;
			for (MonitorStatisticsDataDTO data : entry.getValue().getData()) {
				success += data.getSuccess();
				failure += data.getFailure();
				elapsed += data.getElapsed();
				if (maxElapsed < data.getMaxElapsed()) {
					data.setMaxElapsed(maxElapsed);
				}
				concurrent += data.getConcurrent();
				if (maxConcurrent < data.getMaxConcurrent()) {
					data.setMaxConcurrent(maxConcurrent);
				}
			}
			MonitorStatisticsDataDTOWarpper warpper = entry.getValue();
			warpper.setService(entry.getKey().split("\\|")[0]);
			warpper.setMethod(entry.getKey().split("\\|")[1]);
			warpper.setSuccess(success);
			warpper.setFailure(failure);
			warpper.setElapsed(elapsed);
			warpper.setMaxElapsed(maxElapsed);
			warpper.setConcurrent(concurrent);
			warpper.setMaxConcurrent(maxConcurrent);
		}
		return warpperMap;
	}

	@Override
	public Map<String, MonitorChartDataDTOWarpper> lookupChartData(
			MonitorDataQueryVO queryVO) throws ParseException {
		List<MonitorChartData> chartData = monitorDataDao
				.lookupChartData(queryVO);
		Map<String, MonitorChartDataDTOWarpper> warpper = Maps.newHashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		for (MonitorChartData item : chartData) {
			String method = item.getMethod();
			MonitorChartDataDTOWarpper chartDataDTO = warpper.get(method);
			if (null == chartDataDTO) {
				chartDataDTO = new MonitorChartDataDTOWarpper();
				warpper.put(method, chartDataDTO);
			}
			MonitorChartDataDTO dataList = chartDataDTO.getData().get(
					item.getType());
			if (null == dataList) {
				dataList = new MonitorChartDataDTO();
				chartDataDTO.getData().put(item.getType(), dataList);
			}

			MonitorChartDataItemDTO data = new MonitorChartDataItemDTO();
			data.setLabel(sdf.parse(item.getTime()));
			data.setQps(item.getQps());
			data.setArt(item.getElapsed());

			dataList.getData().add(data);

		}

		// 求和
		for (Map.Entry<String, MonitorChartDataDTOWarpper> entry : warpper
				.entrySet()) {
			for (Map.Entry<String, MonitorChartDataDTO> entry2 : entry
					.getValue().getData().entrySet()) {
				Integer maxQPS = 0;
				Integer minQPS = Integer.MAX_VALUE;
				Integer sumQPS = 0;

				Integer maxArt = 0;
				Integer sumArt = 0;
				for (MonitorChartDataItemDTO item : entry2.getValue().getData()) {
					if (item.getQps() > maxQPS) {
						maxQPS = item.getQps();
					}
					if (item.getQps() < minQPS) {
						minQPS = item.getQps();
					}
					sumQPS += item.getQps();

					if (item.getArt() > maxArt) {
						maxArt = item.getArt();
					}
					sumArt += item.getArt();
				}

				int size = entry2.getValue().getData().size();
				entry2.getValue().setMaxQPS(maxQPS);
				entry2.getValue().setMinQPS(minQPS);
				entry2.getValue().setSumQPS(sumQPS);
				entry2.getValue().setAvgQPS(sumQPS * 1.0 / size);

				entry2.getValue().setMaxArt(maxArt);
				entry2.getValue().setAvgArt(sumArt * 1.0 / size);
			}
		}
		return warpper;
	}
}
