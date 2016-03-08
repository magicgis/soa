package com.yeepay.g3.app.soa.center.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.app.soa.center.utils.NotifyUtils;
import com.yeepay.g3.app.soa.center.utils.SoaCenterConfigUtils;
import com.yeepay.g3.facade.notifier.NotifyFacade;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.facade.AppOwnerFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author by menghao.chen
 */
@Controller
@RequestMapping(value = "/notify")
public class NotifyController extends BaseController {
	@Reference
	private NotifyFacade notifyFacade;

	@Autowired
	private NotifyUtils notifyUtils;

	@Reference
	private AppOwnerFacade appOwnerFacade;

	@Autowired
	private SoaCenterConfigUtils soaCenterConfigUtils;


	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage manualNotify(@RequestParam("appName") String appName,
										@RequestParam(value = "serviceName", required = false) String serviceName,
										@RequestParam("lackInfo") String lackInfo) {
		try {
			CheckUtils.notEmpty(appName, "appName");
			CheckUtils.notEmpty(lackInfo, "lackInfo");
			if (StringUtils.isBlank(serviceName)) {
				serviceName = null;
			}
			List<String> lackInfoList = Arrays.asList(StringUtils.split(lackInfo, "#"));
			doNotify(appName, serviceName, lackInfoList);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	private void doNotify(String appName, String serviceName, List<String> lackInfoList) {
		String lackInfos = StringUtils.join(lackInfoList, "、");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appName", appName);
		if (!StringUtils.isBlank(serviceName)) {
			paramMap.put("serviceNamePlus", "的" + serviceName);
		}
		paramMap.put("lackInfos", lackInfos);
		List<String> recipients = new ArrayList<String>();
		List<AppOwnerDTO> appOwnerDTOList = appOwnerFacade.queryByAppName(appName);
		if (CollectionUtils.isNotEmpty(appOwnerDTOList)) {
			for (AppOwnerDTO owner : appOwnerDTOList) {
				recipients.add(owner.getLoginName() + "@yeepay.com");
			}
			Map<String, Object> notifyConfig = soaCenterConfigUtils.getNotifyAppInfoLackMap();
			String sign = notifyUtils.generateSign((String) notifyConfig.get("userName"),
					(String) notifyConfig.get("ruleName"), (String) notifyConfig.get("secretKey"), recipients);
			notifyFacade.notify((String) notifyConfig.get("userName"), sign, (String) notifyConfig.get("ruleName"),
					recipients, paramMap);
		}
	}

}
