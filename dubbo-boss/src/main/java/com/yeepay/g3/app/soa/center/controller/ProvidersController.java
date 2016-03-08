package com.yeepay.g3.app.soa.center.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;

/**
 * <p>Title: 服务提供者查询控制器</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-27 11:53
 */
@Controller
@RequestMapping(value = "/providers")
public class ProvidersController extends BaseController {

	@Reference
	private ProviderFacade providerFacade;

	@Reference
	private ConsumerFacade consumerFacade;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(@RequestParam(value = "service", required = false) String service,
					   @RequestParam(value = "application", required = false) String application,
					   @RequestParam(value = "address", required = false) String address,
					   Model model) {
		model.addAttribute("serviceList", providerFacade.findServicesSorted());

		List<Provider> providers;
		if (service != null && service.length() > 0) {
			providers = providerFacade.findByService(service);
		} else if (application != null && application.length() > 0) {
			providers = providerFacade.findByApplication(application);
		} else if (address != null && address.length() > 0) {
			providers = providerFacade.findByAddress(address);
		} else {
			providers = providerFacade.findAll();
		}

		// 重新构造服务提供者列表
		Map<String, List<Provider>> providerMap = Maps.newHashMap();
		for (Provider provider : providers) {
			String key = provider.getAddress() + ":" + provider.getService();
			List<Provider> subProvider = providerMap.get(key);
			if (null == subProvider) {
				subProvider = new ArrayList<Provider>();
				providerMap.put(key, subProvider);
			}
			subProvider.add(provider);
		}
		model.addAttribute("providers", providerMap);

		// 应用列表
		Set<String> applications = new TreeSet<String>();
		Set<String> providerApplications = providerFacade.findApplications();
		if (providerApplications != null && providerApplications.size() > 0) {
			applications.addAll(providerApplications);
		}
		model.addAttribute("applications", applications);

		if (blockModel) {
			return "providers/control/listControl";
		} else {
			return "providers/list";
		}
	}

	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id,
						 Model model) {
		Provider provider = providerFacade.findProviderDetail(id);
		model.addAttribute("provider", provider);
		model.addAttribute("paramMap", Tool.toParameterMap(provider.getParameters()));
		return "providers/detail";
	}

	@RequestMapping(value = "/detaillist", method = RequestMethod.GET)
	public String detailList(
			@RequestParam(value = "application", required = false) String application,
			@RequestParam(value = "service", required = false) String serviceName,
			@RequestParam(value = "address", required = false) String address,
			Model model) {
		List<Provider> providers = null;
		if (StringUtils.isNotBlank(application)) {
			providers = this.providerFacade.findByApplication(application);
		} else if (StringUtils.isNotBlank(serviceName)) {
			providers = this.providerFacade.findByService(serviceName);
		} else if (StringUtils.isNotBlank(address)) {
			providers = this.providerFacade.findByAddress(address);
		} else {
			providers = new ArrayList<Provider>(0);
		}
		model.addAttribute("providers", providers);
		return "providers/detaillist";
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("id") Long[] ids) throws ParseException {
		providerFacade.deleteStaticProvider(ids);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/doubling", method = RequestMethod.POST)
	public void doubling(@RequestParam("id") Long[] ids,
						 HttpServletRequest request,
						 HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		providerFacade.doubling(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/halving", method = RequestMethod.POST)
	public void halving(@RequestParam("id") Long[] ids,
						HttpServletRequest request,
						HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		providerFacade.halving(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	public void forbid(@RequestParam("id") Long[] ids,
					   HttpServletRequest request,
					   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		providerFacade.forbid(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/allow", method = RequestMethod.POST)
	public void allow(@RequestParam("id") Long[] ids,
					  HttpServletRequest request,
					  HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		providerFacade.allow(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@RequestMapping(value = "/service/{service}/methods", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage method(@PathVariable(value = "service") String service) {
		Set<String> methods = providerFacade.findMethodsByService(service);
		return new ResponseMessage("methods", methods);
	}

	/**
	 * 根据机器启用服务提供者
	 *
	 * @param address 机器
	 * @return
	 */
	@RequestMapping(value = "/address/{address}/enable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage enableProvidersByAddress(@PathVariable(value = "address") String address,
													HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");

		// 服务提供者地址
		if (!address.contains(":")) {
			List<Provider> providers = providerFacade.findByAddress(address);
			for (Provider provider : providers) {
				if (address.equals(provider.getAddress())) {
					providerFacade.enableProvider(provider.getId());
				}
			}
		} else {
			// 取IP
			address = Tool.getIP(address);
		}

		// 服务提供者地址 & 服务生产者地址
		List<Consumer> consumers = consumerFacade.findByAddress(address);
		for (Consumer consumer : consumers) {
			if (address.equals(consumer.getAddress())) {
				consumerFacade.allow(new Long[]{consumer.getId()}, userDTO.getLoginName());
			}
		}
		return new ResponseMessage("启用服务成功");
	}

	/**
	 * 根据机器禁用服务提供者
	 *
	 * @param address 机器
	 * @return
	 */
	@RequestMapping(value = "/address/{address}/disable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage disableProvidersByAddress(@PathVariable(value = "address") String address,
													 HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");

		// 服务提供者地址
		if (!address.contains(":")) {
			List<Provider> providers = providerFacade.findByAddress(address);
			for (Provider provider : providers) {
				if (address.equals(provider.getAddress())) {
					providerFacade.disableProvider(provider.getId());
				}
			}
		} else {
			// 取IP
			address = Tool.getIP(address);
		}

		// 服务提供者地址 & 服务生产者地址
		List<Consumer> consumers = consumerFacade.findByAddress(address);
		for (Consumer consumer : consumers) {
			if (address.equals(consumer.getAddress())) {
				consumerFacade.forbid(new Long[]{consumer.getId()}, userDTO.getLoginName());
			}
		}
		return new ResponseMessage("禁用服务成功");
	}

}
