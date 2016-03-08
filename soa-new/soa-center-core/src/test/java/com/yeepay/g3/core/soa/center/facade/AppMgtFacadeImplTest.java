package com.yeepay.g3.core.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.utils.rmi.RemoteServiceFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author by menghao.chen
 */
@ContextConfiguration(locations = {"classpath:/soa-center-core-spring/applicationContext-soa-center.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class AppMgtFacadeImplTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private AppMgtFacade appMgtFacade;

	static {
		RemoteServiceFactory.init();
	}

	@Test
	public void testAddUpgradeInfo() {
		AppUpgradeInfoDTO upgradeInfoDTO = new AppUpgradeInfoDTO();
		upgradeInfoDTO.setAppName("soa-center-hessian");
		upgradeInfoDTO.setUpgradeInfo("测试");
		appMgtFacade.addUpgradeInfo(upgradeInfoDTO);
	}
}
