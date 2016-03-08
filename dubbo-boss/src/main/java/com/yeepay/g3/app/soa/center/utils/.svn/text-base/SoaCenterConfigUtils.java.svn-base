package com.yeepay.g3.app.soa.center.utils;

import com.yeepay.g3.utils.config.ConfigParam;
import com.yeepay.g3.utils.config.ConfigurationUtils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by menghao.chen
 */
@Component
public class SoaCenterConfigUtils {
	private static final String CONFIG_TYPE = "config_type_app";
	private static final String SOA_NOTIFY_APP_UPGRADE = "soa.notify.app.upgrade";
	private static final String SIA_NOTIFY_APP_INFO_LACK = "soa.notify.app.info.lack";

	@SuppressWarnings("unchecked")
	public Map<String, Object> getNotifyAppUpgradeInfo() {
		ConfigParam<Map<String, Object>> configParam = ConfigurationUtils
				.getConfigParam(CONFIG_TYPE, SOA_NOTIFY_APP_UPGRADE);
		return configParam.getValue() == null ? new HashMap<String, Object>() : configParam.getValue();
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getNotifyAppInfoLackMap() {
		ConfigParam<Map<String, Object>> configParam = ConfigurationUtils
				.getConfigParam(CONFIG_TYPE, SIA_NOTIFY_APP_INFO_LACK);
		return configParam.getValue() == null ? new HashMap<String, Object>() : configParam.getValue();
	}
}
