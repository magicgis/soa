package com.yeepay.g3.app.soa.center.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.facade.RouteFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Title: 路由规则 控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-26 20:40
 */
@Controller
@RequestMapping("/routes")
public class RoutesController extends BaseController {

	@Reference
	private RouteFacade routeFacade;

	@Reference
	private ProviderFacade providerFacade;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(@RequestParam(value = "service", required = false) String service,
					   @RequestParam(value = "address", required = false) String address,
					   Model model) {
		model.addAttribute("serviceList", providerFacade.findServicesSorted());

		// just for redirect
		if (service == null) {
			service = (String) model.asMap().get("service");
		}

		address = Tool.getIP(address);
		List<Route> routes;
		if (service != null && service.length() > 0
				&& address != null && address.length() > 0) {
			routes = routeFacade.findByServiceAndAddress(service, address);
		} else if (service != null && service.length() > 0) {
			routes = routeFacade.findByService(service);
		} else if (address != null && address.length() > 0) {
			routes = routeFacade.findByAddress(address);
		} else {
			routes = routeFacade.findAll();
		}
		model.addAttribute("routes", routes);

		if (blockModel) {
			return "routes/control/listControl";
		} else {
			return "routes/list";
		}
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(@RequestParam("id") Long id,
					   Model model) throws ParseException {
		Route route = routeFacade.findRoute(id);
		Map<String, String> routeRuleMap = routeFacade.routeRuleParse(id);
		for (Map.Entry entry : routeRuleMap.entrySet()) {
			model.addAttribute((String) entry.getKey(), entry.getValue());
		}

		model.addAttribute("route", route);
		return "routes/detail";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("route", new Route());
		model.addAttribute("formMethod", "add");
		model.addAttribute("applications", providerFacade.findApplications());
		model.addAttribute("serviceList", providerFacade.findServicesSorted());
		return "routes/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam(value = "application", required = false) String application,
					  Route route,
					  HttpServletRequest request,
					  RedirectAttributes redirectAttributes) {
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("yeepay_session_user");
		Map params = request.getParameterMap();
		params.put("operator", userDTO.getLoginName());
		params.put("operatorAddress", request.getRemoteAddr());

		// 如果指定应用但没有指定具体服务，则将路由规则应用到该应用的所有服务
		if (StringUtils.isNotBlank(application)
				&& StringUtils.equals(route.getService(), "*")) {
			List<String> services = providerFacade.findServicesByApplication(application);
			String routeBaseName = route.getName();
			for (String service : services) {
				route.setService(service);
				route.setName(routeBaseName + service);
				routeFacade.createRoute(route, params);
			}
		} else {
			routeFacade.createRoute(route, params);
		}
		redirectAttributes.addFlashAttribute("info", "成功添加路由规则");
		return "redirect:/routes/list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(@RequestParam("id") Long id,
						   Model model) throws ParseException {
		Route route = routeFacade.findRoute(id);
		Map<String, String> routeRuleMap = routeFacade.routeRuleParse(id);
		for (Map.Entry entry : routeRuleMap.entrySet()) {
			model.addAttribute((String) entry.getKey(), entry.getValue());
		}

		model.addAttribute("method", StringUtils.split(routeRuleMap.get("method"), ","));
		model.addAttribute("route", route);
		model.addAttribute("formMethod", "edit");
		model.addAttribute("serviceList", providerFacade.findServicesSorted());
		return "routes/add";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("route") Route route, HttpServletRequest request,
					   RedirectAttributes redirectAttributes) {
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("yeepay_session_user");
		Map params = request.getParameterMap();
		params.put("operator", userDTO.getLoginName());
		params.put("operatorAddress", request.getRemoteAddr());
		routeFacade.updateRoute(route, params);
		redirectAttributes.addFlashAttribute("info", "成功修改路由规则");
		return "redirect:/routes/list";
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("id") Long[] ids) {
		routeFacade.deleteRoute(ids);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	public void forbid(@RequestParam("id") Long[] ids,
					   HttpServletRequest request,
					   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		routeFacade.forbid(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/allow", method = RequestMethod.POST)
	public void allow(@RequestParam("id") Long[] ids,
					  HttpServletRequest request,
					  HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		routeFacade.allow(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

}
