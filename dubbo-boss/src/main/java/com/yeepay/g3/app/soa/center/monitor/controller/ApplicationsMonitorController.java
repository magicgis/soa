package com.yeepay.g3.app.soa.center.monitor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;

/**
 * 应用监控
 *
 * @author：wang.bao
 * @since：2014年9月4日 上午11:26:17
 * @version:
 */
@Controller
@RequestMapping("/monitor/applications")
public class ApplicationsMonitorController extends BaseController {

	@Reference
	private AppMgtFacade appMgtFacade;

	@RequestMapping(value = { "", "/list", "/query" }, method = RequestMethod.GET)
	public String query(Model model) {
		try {
			List<AppInfoDTO> apps = appMgtFacade.queryAllApp(true);
			model.addAttribute("applications", apps);
		} catch (Exception e) {
			LOGGER.error("", e);
			model.addAttribute("applications", new ArrayList<AppInfoDTO>(0));
		}
		return "monitor/applications";
	}
}
