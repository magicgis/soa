/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yeepay.g3.facade.soa.center.facade.ConfigFacade;
import com.yeepay.g3.utils.soa.config.SoaConfigurationUtils;
import com.yeepay.g3.utils.soa.config.model.Config;
import com.yeepay.g3.utils.soa.config.model.ConfigDataTypeEnum;
import com.yeepay.g3.utils.soa.config.model.ConfigValueTypeEnum;
import com.yeepay.g3.utils.soa.consts.SOAConstants;

/**
 *
 * @author：wang.bao
 * @since：2014年10月24日 上午10:31:29
 * @version:
 */
public class ConfigFacadeTest {
	private static ConfigFacade configFacade;

	@BeforeClass
	public static void init() throws Exception {
		HessianProxyFactory factory = new HessianProxyFactory();
		String serviceUrl = "http://localhost:8002/soa-center-hessian/soa/hessian/"
				+ ConfigFacade.class.getName();
		configFacade = (ConfigFacade) factory.create(ConfigFacade.class,
				serviceUrl);
	}

	@Test
	public void testAddConfig1() {
		try {
			this.addConfig("commoncfg", "环境路由", SOAConstants.ROUTER_ENV_KEY,
					"false");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testDeleteConfig1() {
		try {
			configFacade.deleteConfig(SOAConstants.ROUTER_ENV_KEY,
					SOAConstants.CONFIG_COMMONCFG);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testAddConfig2() {
		try {
			this.addConfig("commoncfg", "网络状态路由",
					SOAConstants.ROUTER_NET_KEY, "false");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void addConfig(String app, String name, String key, String value) {
		try {
			Config config = new Config(app, name, key, value,
					ConfigValueTypeEnum.VALUE, ConfigDataTypeEnum.STRING);
			System.out.println(config);
			configFacade.updateConfig(config);
			Boolean _value = SoaConfigurationUtils.getConfig(key,
					Boolean.class, false);
			System.out.println(_value);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
