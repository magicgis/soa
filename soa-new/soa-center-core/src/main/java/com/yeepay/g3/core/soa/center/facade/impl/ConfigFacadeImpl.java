package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.RegistryConfigSync;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.facade.ConfigFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.soa.config.model.Config;

/**
 * 统一配置项管理<br>
 * conf-commoncfg:通用配置<br>
 * conf-xxappliction:应用级配置
 *
 * @author：wang.bao
 * @since：2014年10月24日 下午10:46:51
 * @version:
 */
@Service
public class ConfigFacadeImpl implements ConfigFacade {
	@Autowired
	private RegistryConfigSync registryConfigSync;

	@Override
	public void addConfig(Config config) {
		CheckUtils.notNull(config, "config");
		CheckUtils.notEmpty(config.getDomain(), "domain");
		CheckUtils.notEmpty(config.getName(), "name");
		CheckUtils.notEmpty(config.getKey(), "key");
		CheckUtils.notEmpty(config.getValue(), "value");
		CheckUtils.notEmpty(config.getValueType(), "valueType");
		synchronized (config.getKey()) {
			Config exist = registryConfigSync.getConfig(config.getKey(),
					config.getDomain());
			if (exist != null) {
				throw new IllegalArgumentException("key " + config.getKey()
						+ " is exist!");
			}
			registryConfigSync.saveConfig(config);
		}
	}

	@Override
	public void updateConfig(Config config) {
		CheckUtils.notNull(config, "config");
		CheckUtils.notEmpty(config.getDomain(), "domain");
		CheckUtils.notEmpty(config.getName(), "name");
		CheckUtils.notEmpty(config.getKey(), "key");
		CheckUtils.notEmpty(config.getValue(), "value");
		CheckUtils.notEmpty(config.getValueType(), "valueType");
		synchronized (config.getKey()) {
			registryConfigSync
					.deleteConfig(config.getKey(), config.getDomain());
			registryConfigSync.saveConfig(config);
		}
	}

	@Override
	public void deleteConfig(String key, String domain) {
		CheckUtils.notNull(key, "key");
		CheckUtils.notNull(domain, "domain");
		synchronized (key) {
			registryConfigSync.deleteConfig(key, domain);
		}
	}

	@Override
	public Config findConfig(String key, String domain) {
		CheckUtils.notNull(key, "key");
		CheckUtils.notNull(domain, "domain");
		return registryConfigSync.getConfig(key, domain);
	}

	@Override
	public void changeStatus(String key, String domain, boolean enabled) {
		synchronized (key) {
			Config config = registryConfigSync.getConfig(key, domain);
			if (config != null) {
				registryConfigSync.deleteConfig(key, domain);
				config.setEnabled(enabled);
				registryConfigSync.saveConfig(config);
			}
		}
	}

	@Override
	public PageList queryConfigs(Map<String, String> filters) {
		int pageNo = StringUtils.isBlank(filters.get("pageNo")) ? 1 : Integer
				.parseInt(filters.remove("pageNo"));
		int pageSize = StringUtils.isBlank(filters.get("pageSize")) ? 20
				: Integer.parseInt(filters.remove("pageSize"));
		if (pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize < 1) {
			pageSize = 20;
		}
		List<Config> configs = registryConfigSync.findConfigs(filters);
		return DataConvertUtils.cutToPageList(configs, pageNo, pageSize);
	}
}