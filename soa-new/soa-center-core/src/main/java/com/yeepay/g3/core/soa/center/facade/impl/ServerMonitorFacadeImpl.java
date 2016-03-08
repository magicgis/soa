package com.yeepay.g3.core.soa.center.facade.impl;

import com.alibaba.dubbo.common.Version;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.status.Status;
import com.alibaba.dubbo.common.status.StatusChecker;
import com.alibaba.dubbo.common.status.support.StatusUtils;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.remoting.exchange.ExchangeChannel;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.ServerInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.StatusInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.SystemInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.ServerMonitorFacade;
import com.yeepay.g3.facade.soa.center.param.ClientQueryParam;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.common.page.PageList;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author by menghao.chen
 */
@Service
public class ServerMonitorFacadeImpl implements ServerMonitorFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerMonitorFacadeImpl.class);

	private static final long SECOND = 1000;

	private static final long MINUTE = 60 * SECOND;

	private static final long HOUR = 60 * MINUTE;

	private static final long DAY = 24 * HOUR;

	@Override
	public SystemInfoDTO querySystemInfo() {
		SystemInfoDTO systemInfo = new SystemInfoDTO();
		systemInfo.setVersion(Version.getVersion(Version.class, "2.5.4"));
		String address = NetUtils.getLocalHost();
		systemInfo.setHost(NetUtils.getHostName(address) + "/" + address);
		systemInfo.setOs(System.getProperty("os.name") + " " + System.getProperty("os.version"));
		systemInfo.setJava(System.getProperty("java.runtime.name") + " " + System.getProperty("java.runtime" +
				".version"));
		systemInfo.setCpu(System.getProperty("os.arch", "") + ", "
				+ String.valueOf(Runtime.getRuntime().availableProcessors()) + " cores");
		systemInfo.setLocale(Locale.getDefault().toString() + "/"
				+ System.getProperty("file.encoding"));
		systemInfo.setUptime(formatUptime(ManagementFactory.getRuntimeMXBean().getUptime())
				+ " From " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z").format(new Date(ManagementFactory.getRuntimeMXBean().getStartTime()))
				+ " To " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z").format(new Date()));
		systemInfo.setTime(new Date());
		return systemInfo;
	}

	@Override
	public List<ServerInfoDTO> queryServerInfos() {
		List<ServerInfoDTO> serverInfoList = new ArrayList<ServerInfoDTO>();
		Collection<ExchangeServer> servers = DubboProtocol.getDubboProtocol().getServers();
		if (CollectionUtils.isNotEmpty(servers)) {
			for (ExchangeServer server : servers) {
				ServerInfoDTO serverInfo = new ServerInfoDTO();
				String address = server.getUrl().getAddress();
				Integer clientSize = server.getExchangeChannels().size();
				serverInfo.setAddress(NetUtils.getHostName(address) + "/" + address);
				serverInfo.setPort(server.getUrl().getPort());
				serverInfo.setClientSize(clientSize);
				serverInfoList.add(serverInfo);
			}
		}
		return serverInfoList;
	}

	@Override
	public List<StatusInfoDTO> queryRuntimeStatus() {
		List<StatusInfoDTO> statusInfoList = new ArrayList<StatusInfoDTO>();
		ExtensionLoader<StatusChecker> loader = ExtensionLoader.getExtensionLoader(StatusChecker.class);
		Map<String, Status> statusList = new LinkedHashMap<String, Status>();
		for (String name : loader.getSupportedExtensions()) {
			StatusInfoDTO statusInfo = new StatusInfoDTO();
			Status status = loader.getExtension(name).check();
			if (status.getLevel() != null && status.getLevel() != com.alibaba.dubbo.common.status.Status.Level.UNKNOWN) {
				statusList.put(name, status);
				statusInfo.setName(name);
				statusInfo.setLevel(status.getLevel().name());
				statusInfo.setDescription(status.getMessage());
			}
			statusInfoList.add(statusInfo);
		}
		Status summary = StatusUtils.getSummaryStatus(statusList);
		StatusInfoDTO statusInfo = new StatusInfoDTO();
		statusInfo.setName("summary");
		statusInfo.setLevel(summary.getLevel().name());
		statusInfo.setDescription(summary.getDescription());
		statusInfoList.add(statusInfo);
		return statusInfoList;
	}

	@Override
	public PageList queryClient(ClientQueryParam queryParam) {
		List<String> clients = this.query(queryParam);
		Collections.sort(clients);
		PageList ret = DataConvertUtils.cutToPageList(clients,
				queryParam.getPageNo(), queryParam.getPageSize());
		return ret;
	}

	private List<String> query(ClientQueryParam clientQueryParam) {
		List<String> result = new ArrayList<String>();
		Integer port = clientQueryParam.getPort();
		String address = clientQueryParam.getAddress();
		Collection<ExchangeServer> servers = DubboProtocol.getDubboProtocol().getServers();
		ExchangeServer server = null;
		if (CollectionUtils.isNotEmpty(servers)) {
			if (servers.size() == 1) {
				server = servers.iterator().next();
			} else {
				for (ExchangeServer s : servers) {
					int sp = s.getUrl().getPort();
					if (port == 0 && server == null || port == sp) {
						server = s;
					}
				}
			}
		}
		if (server != null) {
			Collection<ExchangeChannel> channels = server.getExchangeChannels();
			for (ExchangeChannel c : channels) {
				String url = NetUtils.toAddressString(c.getRemoteAddress());
				String clientAddress = NetUtils.getHostName(url) + "/" + url;
				if (StringUtils.isNotBlank(clientAddress) && (StringUtils.isBlank(address)
						|| clientAddress.contains(address))) {
					result.add(clientAddress);
				}
			}
		}
		return result;
	}


	private String formatUptime(long uptime) {
		StringBuilder buf = new StringBuilder();
		if (uptime > DAY) {
			long days = (uptime - uptime % DAY) / DAY;
			buf.append(days);
			buf.append(" Days");
			uptime = uptime % DAY;
		}
		if (uptime > HOUR) {
			long hours = (uptime - uptime % HOUR) / HOUR;
			if (buf.length() > 0) {
				buf.append(", ");
			}
			buf.append(hours);
			buf.append(" Hours");
			uptime = uptime % HOUR;
		}
		if (uptime > MINUTE) {
			long minutes = (uptime - uptime % MINUTE) / MINUTE;
			if (buf.length() > 0) {
				buf.append(", ");
			}
			buf.append(minutes);
			buf.append(" Minutes");
			uptime = uptime % MINUTE;
		}
		if (uptime > SECOND) {
			long seconds = (uptime - uptime % SECOND) / SECOND;
			if (buf.length() > 0) {
				buf.append(", ");
			}
			buf.append(seconds);
			buf.append(" Seconds");
			uptime = uptime % SECOND;
		}
		if (uptime > 0) {
			if (buf.length() > 0) {
				buf.append(", ");
			}
			buf.append(uptime);
			buf.append(" Milliseconds");
		}
		return buf.toString();
	}

}
