/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import org.junit.Assert;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yeepay.g3.facade.soa.center.enums.QueryTypeEnum;
import com.yeepay.g3.facade.soa.center.facade.RegistrySyncFacade;
import com.yeepay.g3.facade.soa.center.facade.ServiceQueryFacade;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author：wang.bao
 * @since：2014年8月13日 上午11:01:38
 * @version:
 */
public class SoaCenterHessianTest {

	@Test
	public void testRegistrySync() {
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			String serviceUrl = "http://localhost:8002/soa-center-hessian/soa/hessian/"
					+ RegistrySyncFacade.class.getName();
			RegistrySyncFacade registyrSyncFacde = (RegistrySyncFacade) factory
					.create(RegistrySyncFacade.class, serviceUrl);
			registyrSyncFacde.syncRegistry();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testQueryService() {
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			String serviceUrl = "http://localhost:8002/soa-center-hessian/soa/hessian/"
					+ ServiceQueryFacade.class.getName();
			ServiceQueryFacade serviceQueryFacade = (ServiceQueryFacade) factory
					.create(ServiceQueryFacade.class, serviceUrl);
			ServiceQueryParam queryParam = new ServiceQueryParam();
			queryParam.setQueryInput("settle");
			queryParam.setQueryType(QueryTypeEnum.SERVICE);
			PageList result = serviceQueryFacade.queryService(queryParam);
			Assert.assertNotNull(result);
			Assert.assertTrue(result.size() > 0);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
