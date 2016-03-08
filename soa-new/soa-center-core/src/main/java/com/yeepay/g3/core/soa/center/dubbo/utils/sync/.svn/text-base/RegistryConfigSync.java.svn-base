package com.yeepay.g3.core.soa.center.dubbo.utils.sync;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.URL;
import com.google.common.collect.Lists;
import com.yeepay.g3.utils.soa.config.SoaConfigurationRepository;
import com.yeepay.g3.utils.soa.config.model.Config;

/**
 * 同步统一配置
 *
 * @author：wang.bao
 * @since：2014年10月24日 下午7:53:57
 * @version:
 */
@Component
public class RegistryConfigSync {
	private static final String CONF_KEY_KEY = "_key";
	private static final String CONF_KEY_NAME = "_name";

	@Autowired
	private RegistryServerSync registryServerSync;

	private static SoaConfigurationRepository configRepository = new SoaConfigurationRepository();

	public void saveConfig(Config config) {
		registryServerSync.register(config.toURL());
	}

	public void deleteConfig(String key, String domain) {
		Config config = configRepository.getConfigObject(key, domain);
		if (config != null) {
			registryServerSync.unregister(config.getOrigUrl());
		}
	}

	public Config getConfig(String key, String domain) {
		return configRepository.getConfigObject(key, domain);
	}

	public List<Config> findAllConfig() {
		return configRepository.getAllConfig();
	}

	public List<Config> findConfigs(Map<String, String> filters) {
		List<Config> allConfigs = findAllConfig();
		if (filters == null || filters.isEmpty()) {
		}
		List<Config> result = Lists.newArrayList();
		String configName = filters.remove(CONF_KEY_NAME);
		String configKey = filters.remove(CONF_KEY_KEY);
		for (Config config : allConfigs) {
			if (this.isMatch(config.getOrigUrl(), configName, configKey,
					filters)) {
				result.add(config);
			}
		}
		return result;
	}

	private boolean isMatch(URL url, String configName, String configKey,
			Map<String, String> filters) {
		if (url == null) {
			return false;
		}
		Map<String, String> params = url.getParameters();
		if (StringUtils.isNotBlank(configName)
				&& !StringUtils.containsIgnoreCase(
						URL.decode(params.get(CONF_KEY_NAME)), configName)) {
			return false;
		}
		if (StringUtils.isNotBlank(configKey)
				&& !StringUtils.containsIgnoreCase(
						URL.decode(params.get(CONF_KEY_KEY)), configKey)) {
			return false;
		}
		for (String key : filters.keySet()) {
			String value = filters.get(key);
			if (StringUtils.isNotBlank(value)
					&& !StringUtils.equals(params.get(key), value)) {
				return false;
			}
		}
		return true;
	}

	public static void notify(List<URL> urls) {
		configRepository.refresh(urls);
	}
}
