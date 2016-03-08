package com.yeepay.g3.core.soa.center.dao;

import com.yeepay.g3.core.soa.center.BaseBizTest;
import com.yeepay.g3.core.soa.center.entity.MonitorData;
import com.yeepay.g3.core.soa.center.entity.MonitorDataBuilder;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Title: 监控数据 DAO 测试类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 13:25
 */
public class MonitorDataTest extends BaseBizTest {

	@Autowired
	private MonitorDataDao monitorDataDao;

	@Test
	public void testInsert() throws Exception {
		MonitorDataBuilder monitorDataBuilder = new MonitorDataBuilder();
		monitorDataBuilder.setDateStr("20140910").setService("com.alibaba.dubbo.monitor.MonitorFacade").setMethod("collect")
				.setConsumer("127.0.0.1").setProvider("50.1.1.22").setType("consumer")
				.setSuccess(10)
				.setFailure(2)
				.setElapsed(234)
				.setConcurrent(2)
				.setMaxElapsed(500)
				.setMaxConcurrent(11);
		MonitorData monitorData = monitorDataBuilder.createMonitorData();
		monitorDataDao.insert(monitorData);
		Assert.assertNotNull(monitorData.getId());
	}

}
