package com.yeepay.g3.app.soa.center.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.employee.facade.UserFacade;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.AppOwnerFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.rmi.RemoteServiceFactory;

/**
 * <p>
 * Title: 负责人查询控制器
 * </p>
 * <p>
 * Description: 描述
 * </p>
 * <p>
 * Copyright: Copyright (c)2011
 * </p>
 * <p>
 * Company: 易宝支付(YeePay)
 * </p>
 *
 * @author baitao.ji wang.bao
 * @version 0.1, 14-6-26 12:05
 */
@Controller
@RequestMapping("/owners")
public class OwnersController extends BaseController {
	@Reference
	private AppMgtFacade appMgtFacade;

	@Reference
	private AppOwnerFacade appOwnerFacade;

	private UserFacade userFacade = RemoteServiceFactory
			.getService(UserFacade.class);

	/**
	 * 新增负责人
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addOwner(
			@RequestParam(value = "appName", required = true) String appName,
			@RequestParam(value = "ownerId", required = false) Long ownerId,
			@RequestParam(value = "loginName", required = false) String loginName,
			@RequestParam(value = "ownerName", required = false) String ownerName,
			Model model) {
		try {
			AppOwnerDTO owner = new AppOwnerDTO();
			owner.setAppName(appName);
			owner.setLoginName(loginName);
			if (StringUtils.isBlank(loginName)
					&& StringUtils.isBlank(ownerName)) {
				throw new IllegalArgumentException("负责人登录名和真实姓名不能同时为空");
			}
			if (StringUtils.isBlank(ownerName)) {
				UserDTO user = userFacade.queryUserByLoginName(loginName);
				if (user != null) {
					ownerName = user.getUserName();
				} else {
					ownerName = loginName;
				}
			}
			owner.setOwnerName(ownerName);
			appMgtFacade.addOwner(owner);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除负责人
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteOwner(
			@RequestParam(value = "ownerId", required = true) Long ownerId,
			Model model) {
		try {
			appMgtFacade.deleteOwner(ownerId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String queryOwner(@RequestParam(value = "loginName", required = false) String loginName,
							 Model model) {
		Map<String, Boolean> appOwnerMap = new HashMap<String, Boolean>();
		List<AppInfoDTO> allApps = appMgtFacade.queryAllApp(false);
		if (CollectionUtils.isNotEmpty(allApps)) {
			for (AppInfoDTO appInfo : allApps) {
				appOwnerMap.put(appInfo.getAppName(), Boolean.FALSE);
			}
		}
		List<AppOwnerDTO> ownApps = appOwnerFacade.queryByLoginName(loginName);
		if (CollectionUtils.isNotEmpty(ownApps)) {
			for (AppOwnerDTO appOwnerDTO : ownApps) {
				appOwnerMap.put(appOwnerDTO.getAppName(), Boolean.TRUE);
			}
		}
		model.addAttribute("appOwnerMap", appOwnerMap);
		model.addAttribute("loginName", loginName);
		return "owner/batchManagement";
	}

	@RequestMapping(value = "batchUpdate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage batchUpdate(@RequestParam(value = "loginName") String loginName,
									   @RequestParam(value = "ownApp", required = false) String[] ownAppNames) {
		try {
			CheckUtils.notEmpty(loginName, "loginName");
			UserDTO user = userFacade.queryUserByLoginName(loginName);
			if (user == null) {
				throw new IllegalArgumentException("登录名" + loginName + "不存在");
			}

			List<String> ownAppNameList = null;
			if (ownAppNames != null) {
				ownAppNameList = Arrays.asList(ownAppNames);
			}
			appOwnerFacade.batchUpdate(loginName, user.getUserName(), ownAppNameList);
			return new ResponseMessage(null);
		} catch (Exception e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}
}
