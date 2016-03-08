/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.caucho.hessian.client.HessianProxyFactory;
import com.yeepay.g3.utils.soa.log.BizLogDTO;
import com.yeepay.g3.utils.soa.service.SoaBizLogService;

/**
 *
 * @author：wang.bao
 * @since：2014年10月24日 上午10:31:29
 * @version:
 */
public class BizLogFacadeTest {
	private static SoaBizLogService soaBizLogService;

	@BeforeClass
	public static void init() throws Exception {
		HessianProxyFactory factory = new HessianProxyFactory();
		String serviceUrl = "http://50.1.1.22:8095/soa-center-hessian/soa/hessian/"
				+ SoaBizLogService.class.getName();
		soaBizLogService = (SoaBizLogService) factory.create(
				SoaBizLogService.class, serviceUrl);
	}

	@Test
	public void testSaveLog() {
		try {
			BizLogDTO bizLog = new BizLogDTO();
			bizLog.setApplication("biz-log-test");
			bizLog.setLoggerName(this.getClass().getName());
			bizLog.setLogContent("testLogContent");
			bizLog.setLogTable(false);
			bizLog.setThreadName(Thread.currentThread().getName());
			bizLog.setCreateTime(new Date());
			bizLog.setHost(NetUtils.getLocalHost());
			soaBizLogService.save(bizLog);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
