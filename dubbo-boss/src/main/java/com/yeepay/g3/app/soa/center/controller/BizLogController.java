package com.yeepay.g3.app.soa.center.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.ConfigFacade;

/**
 * 业务日志查询
 *
 * @author：wang.bao
 * @since：2014年11月6日 下午4:51:35
 * @version:
 */
@Controller
@RequestMapping("/logs")
public class BizLogController extends BaseController {
	@Reference
	private ConfigFacade configFacade;

	@Reference
	private AppMgtFacade appMgtFacade;

	/**
	 * 业务日志查询
	 */
	@RequestMapping(value = { "/", "/list", "/query" }, method = RequestMethod.GET)
	public String query(Model model) {
		List<AppInfoDTO> applications = appMgtFacade.queryAllApp(true);
		model.addAttribute("applications", applications);
		return "logs/query";
	}
}
