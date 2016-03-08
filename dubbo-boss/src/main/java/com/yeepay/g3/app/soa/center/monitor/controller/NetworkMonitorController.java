/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.app.soa.center.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.NetworkFacade;
import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author：wang.bao
 * @since：2014年9月22日 上午9:48:41
 * @version:
 */
@Controller
@RequestMapping("/monitor/networks")
public class NetworkMonitorController extends BaseController {

	@Reference
	private NetworkFacade networkFacade;

	@RequestMapping(value = { "", "/query" }, method = RequestMethod.GET)
	public String query(
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "appName", required = false) String appName,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "status", required = false) NetworkStatusEnum status,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		NetworkQueryParam queryParam = new NetworkQueryParam();
		queryParam.setAddress(address);
		queryParam.setAppName(appName);
		queryParam.setType(type);
		queryParam.setStatus(status);
		queryParam.setPageNo(pageNo);
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		queryParam.setPageSize(pageSize);
		PageList result = networkFacade.queryNetwork(queryParam);
		model.addAttribute("result", result);
		return "monitor/networks";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage delete(
			@RequestParam(value = "networkid", required = true) Long networkId,
			Model model) {
		try {
			networkFacade.deleteNetworkInfo(networkId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/clearUnknown", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage clearUnknown(Model model) {
		try {
			networkFacade.clearUnknown();
			;
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}
}
