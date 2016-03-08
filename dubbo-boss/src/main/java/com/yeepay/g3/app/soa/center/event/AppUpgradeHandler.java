package com.yeepay.g3.app.soa.center.event;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.utils.NotifyUtils;
import com.yeepay.g3.app.soa.center.utils.SoaCenterConfigUtils;
import com.yeepay.g3.app.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.notifier.NotifyFacade;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.AppOwnerFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.DateUtils;
import com.yeepay.g3.utils.event.ext.BaseEventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by menghao.chen
 */
@Component
public class AppUpgradeHandler extends BaseEventListener {
	@Reference
	private NotifyFacade notifyFacade;

	@Autowired
	private NotifyUtils notifyUtils;

	@Reference
	private AppOwnerFacade appOwnerFacade;

	@Autowired
	private SoaCenterConfigUtils soaCenterConfigUtils;

	@Override
	public String getListenedEventName() {
		return SoaCenterConst.SOA_APP_UPGRADE;
	}

	@Override
	public void doAction(Object... params) {
		if (params == null || params.length <= 0) {
			return;
		}
		AppUpgradeInfoDTO appUpgradeInfo;
		if (params[0] instanceof AppUpgradeInfoDTO) {
			appUpgradeInfo = (AppUpgradeInfoDTO) params[0];
		} else {
			throw new IllegalArgumentException("异步事件参数类型不正确");
		}
		//根据应用名查出依赖该应用的消费者列表
		List<AppOwnerDTO> appOwnerList = appOwnerFacade.queryByDepAppName(appUpgradeInfo.getAppName());
		if (CheckUtils.isEmpty(appOwnerList)) {
			return;
		}
		Map<String, Object> notifyConfig = soaCenterConfigUtils.getNotifyAppUpgradeInfo();
		Map<String, Map<String, Object>> paramMap = prepareParam(appUpgradeInfo, appOwnerList);
		for (Map.Entry<String, Map<String, Object>> entry : paramMap.entrySet()) {
			List<String> recipients = new ArrayList<String>();
			recipients.add(entry.getKey());
			String sign = notifyUtils.generateSign((String) notifyConfig.get("userName"), (String) notifyConfig.get("ruleName"),
					(String) notifyConfig.get("secretKey"), recipients);
			notifyFacade.notify((String) notifyConfig.get("userName"), sign, (String) notifyConfig.get("ruleName"), recipients,
					entry.getValue());
		}
	}

	private Map<String, Map<String, Object>> prepareParam(AppUpgradeInfoDTO appUpgradeInfo,
														  List<AppOwnerDTO> appOwnerList) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		for (AppOwnerDTO owner : appOwnerList) {
			String recipient = owner.getLoginName() + "@yeepay.com";
			if (result.get(recipient) == null) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("appName", owner.getAppName());
				param.put("providerName", appUpgradeInfo.getAppName());
				param.put("updateDate", DateUtils.getLongDateStr());
				param.put("updateInfo", appUpgradeInfo.getUpgradeInfo());
				result.put(recipient, param);
			} else {
				Map<String, Object> param = result.get(recipient);
				String appNames = (String) param.get("appName");
				param.put("appName", appNames + '、' + owner.getAppName());
			}
		}
		return result;
	}

}
