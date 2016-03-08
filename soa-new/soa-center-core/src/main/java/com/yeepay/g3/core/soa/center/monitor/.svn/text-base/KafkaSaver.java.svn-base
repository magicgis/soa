/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.monitor;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.json.JSON;
import com.yeepay.g3.core.soa.center.entity.MonitorData;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.config.ConfigParam;
import com.yeepay.g3.utils.config.ConfigurationUtils;

/**
 * @author：wang.bao
 * @since：2015年8月18日 上午9:11:57
 * @version:
 */
@Component
public class KafkaSaver extends AbstractMonitorDataSaver {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(KafkaSaver.class);

	private Producer<String, String> producer;
	private String topic;

	public KafkaSaver() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doInit() {
		String broker = null;
		ConfigParam<String> brokerConfig = ConfigurationUtils
				.getSysConfigParam(SoaCenterConst.CONFIG_KEY_KAFKA_CLUSTER);
		if (brokerConfig != null) {
			broker = brokerConfig.getValue();
		}
		if (StringUtils.isBlank(broker)) {
			broker = "10.148.180.21:9092,10.148.180.22:9092,10.148.180.23:9092";
		}
		Properties props = new Properties();
		props.put("metadata.broker.list", broker);
		// 默认字符串编码消息
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// 使用默认的散列方式
		props.put("partitioner.class", "kafka.producer.DefaultPartitioner");
		props.put("request.required.acks", "1");
		// props.put("auto.create.topics.enable", "true");
		ProducerConfig config = new ProducerConfig(props);
		this.producer = new Producer<String, String>(config);
		this.topic = SoaCenterConst.MQ_EXCHANGE_NAME;
		logger.info("init kafka success!");
	}

	@Override
	public String getName() {
		return "kafka";
	}

	@Override
	protected void doSave(MonitorData data) throws Exception {
		LOGGER.debug("测试发送的数据内容格式 {}", JSON.json(data));
		String message = JSON.json(data);
		KeyedMessage<String, String> msg = new KeyedMessage<String, String>(
				topic, message);
		producer.send(msg);
	}

	@Override
	protected void doDestory() {
		this.producer.close();
	}
}
