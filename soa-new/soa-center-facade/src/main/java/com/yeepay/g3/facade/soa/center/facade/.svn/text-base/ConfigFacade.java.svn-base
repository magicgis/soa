package com.yeepay.g3.facade.soa.center.facade;

import java.util.Map;

import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.soa.config.model.Config;

/**
 * Title: 配置 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/>
 * <br/>
 *
 * @author baitao.ji wang.bao
 * @version 0.1, 14-8-22 18:31
 */
public interface ConfigFacade {

	void addConfig(Config config);

	void updateConfig(Config config);

	void changeStatus(String key, String domain, boolean enabled);

	void deleteConfig(String key, String domain);

	Config findConfig(String key, String domain);

	PageList queryConfigs(Map<String, String> filters);
}
