/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.monitor;

import java.util.HashMap;
import java.util.Map;

import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.json.JSON;
import com.yeepay.g3.core.soa.center.entity.MonitorData;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.utils.async.rabbitmq.RabbitMQManager;
import com.yeepay.g3.utils.async.rabbitmq.send.DefaultRabbitMQSender;
import com.yeepay.g3.utils.async.send.AsyncEventSender;

/**
 * @author：wang.bao
 * @since：2015年8月18日 上午9:11:57
 * @version:
 */
@Component
public class RabbitmqSaver extends AbstractMonitorDataSaver {

    protected static final Logger logger = LoggerFactory.getLogger(RabbitmqSaver.class);

	private AsyncEventSender sender;

	private RabbitMQManager rabbitMQManager;

	@Override
	public String getName() {
		return "rabbitmq";
	}

	public RabbitmqSaver() {
		super();
	}

	protected void doInit() throws Exception {
		Map<String, String> exchage2queue = new HashMap<String, String>();
		exchage2queue.put(SoaCenterConst.MQ_EXCHANGE_NAME,
				SoaCenterConst.MQ_EXCHANGE_NAME);
		rabbitMQManager = new RabbitMQManager();
		rabbitMQManager.setExchage2queue(exchage2queue);
		rabbitMQManager.afterPropertiesSet();
		DefaultRabbitMQSender rabbitMQSender = new DefaultRabbitMQSender();
		rabbitMQSender.setRabbitMQManager(rabbitMQManager);
		sender = new AsyncEventSender();
		sender.setRabbitMQSender(rabbitMQSender);
		logger.info("init rabbitmq success!");
	}

	@Override
	protected void doDestory() throws Exception {
		rabbitMQManager.destroy();
	}

	@Override
	protected void doSave(MonitorData data) throws Exception {
		logger.debug("测试发送的数据内容格式 {}", JSON.json(data));
		// 发送监控数据到 RabbitMQ
		AsyncEventSender.send(SoaCenterConst.MQ_EXCHANGE_NAME, JSON.json(data),
				false, false);
	}
}
