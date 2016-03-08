/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.BaseBizTest;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author：wang.bao
 * @since：2014年8月19日 上午11:23:16
 * @version:
 */
public class ServiceInfoDaoTest extends BaseBizTest {
	@Autowired
	private ServiceInfoDao serviceInfoDao;

	@Test
	public void testQueryServiceInfo01() {
		ServiceQueryParam queryParam = new ServiceQueryParam();
		queryParam.setQueryInput("settle");
		queryParam.setPageNo(1);
		queryParam.setPageSize(2);
		PageList result = serviceInfoDao.queryServiceInfo(queryParam);
		System.err.println(result);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.size() > 0);
	}

	@Test
	public void testQueryServiceInfo02() {
		ServiceQueryParam queryParam = new ServiceQueryParam();
		queryParam.setQueryInput("dubbo");
		queryParam.addExtParam("appName", "dubbo-monitor-simple");

		queryParam.setPageNo(1);
		queryParam.setPageSize(20);
		PageList result = serviceInfoDao.queryServiceInfo(queryParam);
		int size1 = result.size();
		System.err.println(size1 + " | " + result);
		Assert.assertNotNull(result);
		Assert.assertTrue(size1 > 0);

		queryParam.addExtParam("appName", "soa-center-hessian");
		PageList result2 = serviceInfoDao.queryServiceInfo(queryParam);
		int size2 = result2.size();
		System.err.println(size2 + " | " + result2);
		Assert.assertNotNull(result2);
		Assert.assertTrue(size2 > 0);
		Assert.assertTrue(size2 > size1);
	}
}
