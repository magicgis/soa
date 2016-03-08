package com.yeepay.g3.app.soa.center.monitor.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.facade.IDCControlFacade;

/**
 * 监控控制面板
 *
 * @author：wang.bao
 * @since：2015年7月24日 下午5:04:55
 * @version:
 */
@Controller
@RequestMapping("/monitor")
public class DashboadController extends BaseController {

	@Reference
	private IDCControlFacade iDCControlFacade;

	/**
	 * 打开首页
	 */
	@RequestMapping(value = { "", "/dashboad" }, method = RequestMethod.GET)
	public String index(Model model) {
		try {
			Map<String, Object> info = iDCControlFacade.queryInfo();
			model.addAllAttributes(info);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return "monitor/dashboad";
	}

	/**
	 * 切换IDC
	 */
	@ResponseBody
	@RequestMapping(value = { "/idc/status" })
	public ResponseMessage changeIdcStatus(Model model,
			@RequestParam(value = "idcName", required = true) String idcName,
			@RequestParam(value = "status", required = true) boolean status) {
		try {
			if (status) {
				iDCControlFacade.enableIDC(idcName);
			} else {
				iDCControlFacade.disableIDC(idcName);
			}
			return new ResponseMessage(null);
		} catch (Throwable e) {
			LOGGER.error("", e);
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 修改IDC-IP规则
	 */
	@ResponseBody
	@RequestMapping(value = { "/idc/iprule" })
	public ResponseMessage changeIdcIPRule(Model model,
			@RequestParam(value = "idcName", required = true) String idcName,
			@RequestParam(value = "ipRule", required = true) String ipRule) {
		try {
			iDCControlFacade.changeIPRule(idcName, ipRule);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			LOGGER.error("", e);
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 修改IDC-跨机房流量比例
	 */
	@ResponseBody
	@RequestMapping(value = { "/idc/crossrate" })
	public ResponseMessage changeCrossRate(Model model,
			@RequestParam(value = "rate", required = true) int rate) {
		try {
			if (rate < 0 || rate > 100) {
				throw new IllegalArgumentException("跨机房流量比例必须在0-100之间");
			}
			iDCControlFacade.changeCrossRate(rate);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			LOGGER.error("", e);
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}
}
