package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.facade.soa.center.facade.ConfigFacade;
import com.yeepay.g3.facade.soa.center.facade.IDCControlFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.exception.YeepayRuntimeException;
import com.yeepay.g3.utils.soa.config.SoaConfigurationUtils;
import com.yeepay.g3.utils.soa.config.model.Config;
import com.yeepay.g3.utils.soa.consts.SOAConstants;

/**
 *
 * @author：wang.bao
 * @since：2015年7月28日 下午3:53:28
 * @version:
 */
@Service
public class IDCControlFacadeImpl implements IDCControlFacade {
	@Autowired
	private ConfigFacade configFacade;

	@Override
	public Map<String, Object> queryInfo() {
		Map<String, Object> info = new HashMap<String, Object>();
		Config rateConfig = configFacade.findConfig(
				SOAConstants.ROUTER_IDC_CROSS_RATE,
				SOAConstants.CONFIG_COMMONCFG);
		Config switchConfig = configFacade.findConfig(
				SOAConstants.ROUTER_IDC_SWITCH, SOAConstants.CONFIG_COMMONCFG);
		Config ipRuleConfig = configFacade.findConfig(
				SOAConstants.ROUTER_IDC_IP_RULE, SOAConstants.CONFIG_COMMONCFG);
		if (rateConfig != null) {
			info.put(SOAConstants.ROUTER_IDC_CROSS_RATE.replaceAll("\\.", "_"),
					rateConfig.getValue());
		}
		if (switchConfig != null) {
			info.put(SOAConstants.ROUTER_IDC_SWITCH.replaceAll("\\.", "_"),
					switchConfig.getValue());
		}
		if (ipRuleConfig != null) {
			info.put(SOAConstants.ROUTER_IDC_IP_RULE.replaceAll("\\.", "_"),
					ipRuleConfig.getValue());
		}
		return info;
	}

	@Override
	public void disableIDC(String idcName) {
		changeIDCStatus(idcName, false);
	}

	@Override
	public void enableIDC(String idcName) {
		changeIDCStatus(idcName, true);
	}

	@Override
	public void changeCrossRate(int rate) {
		if (rate < 0 || rate > 100) {
			throw new IllegalArgumentException("跨机房流量比例必须在0-100之间");
		}
		Config config = SoaConfigurationUtils
				.getConfigObject(SOAConstants.ROUTER_IDC_CROSS_RATE);
		if (config == null) {
			config = new Config(SOAConstants.CONFIG_COMMONCFG, "IDC-跨机房流量比例",
					SOAConstants.ROUTER_IDC_CROSS_RATE, rate);
			configFacade.addConfig(config);
		} else {
			config.setValue(rate);
			configFacade.updateConfig(config);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void changeIPRule(String idcName, String ipRule) {
		CheckUtils.notEmpty(idcName, "idcName");
		Config config = SoaConfigurationUtils
				.getConfigObject(SOAConstants.ROUTER_IDC_IP_RULE);
		if (config == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(idcName, ipRule);
			config = new Config(SOAConstants.CONFIG_COMMONCFG, "IDC-IP规则",
					SOAConstants.ROUTER_IDC_IP_RULE, map);
			configFacade.addConfig(config);
		} else {
			Map<String, String> map = (Map<String, String>) config.getValue();
			map.put(idcName, ipRule);
			configFacade.updateConfig(config);
		}
	}

	@SuppressWarnings("unchecked")
	private void changeIDCStatus(String idcName, boolean status) {
		CheckUtils.notEmpty(idcName, "idcName");
		Config config = SoaConfigurationUtils
				.getConfigObject(SOAConstants.ROUTER_IDC_SWITCH);
		if (config == null) {
			Map<String, Boolean> map = new HashMap<String, Boolean>();
			map.put(idcName, status);
			config = new Config(SOAConstants.CONFIG_COMMONCFG, "IDC-机房开关",
					SOAConstants.ROUTER_IDC_SWITCH, map);
		} else {
			Map<String, Boolean> map = (Map<String, Boolean>) config.getValue();
			map.put(idcName, status);
			boolean allDisabled = true;
			for (Boolean s : map.values()) {
				if (s == null || s) {
					allDisabled = false;
					break;
				}
			}
			if (allDisabled) {
				throw new YeepayRuntimeException("不能禁用" + idcName
						+ "机房,否则所有机房都将被禁用");
			}
		}
		configFacade.updateConfig(config);
	}
}
