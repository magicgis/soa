package com.yeepay.g3.app.soa.center.controller;

import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Sets;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.facade.OverrideFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.param.AddressQueryParam;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;

/**
 * <p>
 * Title: 机器查询控制器
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
 * @version 0.1, 14-6-30 14:46
 */
@Controller
@RequestMapping("/addresses")
public class AddressesController extends BaseController {

	@Reference
	private ProviderFacade providerFacade;

	@Reference
	private ConsumerFacade consumerFacade;

	@Reference
	private OverrideFacade overrideFacade;

	@Reference
	private AddressFacade addressFacade;

	/**
	 * 机器查询
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String query(
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "appName", required = false) String appName,
			@RequestParam(value = "serviceName", required = false) String serviceName,
			@RequestParam(value = "environment", required = false) String environment,
			@RequestParam(value = "side", required = false) String side,
			@RequestParam(value = "role", required = false) AddressRoleEnum role,
			@RequestParam(value = "status", required = false) SoaStatusEnum status,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "ccache", required = false, defaultValue = "false") Boolean ccache,
			Model model) {
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setAddress(address);
		queryParam.setAppName(appName);
		queryParam.setServiceName(serviceName);
		queryParam.setSide(side);
		queryParam.setEnvironment(environment);
		queryParam.setStatus(status);
		queryParam.setRole(role);
		queryParam.setPageNo(pageNo);
		queryParam.setWithCache(!ccache);
		if (blockModel) {
			pageSize = Integer.MAX_VALUE;
		} else if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		PageList result = addressFacade.queryAddress(queryParam);
		model.addAttribute("result", result);
		if (blockModel) {
			return "addresses/control/query";
		}
		return "addresses/query";
	}

	/**
	 * 查询机器
	 *
	 * @param type
	 *            类型
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "type", required = false) String type,
			Model model) {
		Set<String> addresses = new TreeSet<String>();
		Set<String> providerAddresses = Sets.newHashSet();
		Set<String> consumerAddresses = Sets.newHashSet();

		// 服务提供者
		if (!"consumer".equals(type)) {
			providerAddresses = providerFacade.findAddresses();
			addresses.addAll(providerAddresses);
		}

		// 服务消费者
		if (!"provider".equals(type)) {
			consumerAddresses = consumerFacade.findAddresses();
			addresses.addAll(consumerAddresses);
		}

		// 应用列表
		Set<String> applications = new TreeSet<String>();
		Set<String> providerApplications = providerFacade.findApplications();
		Set<String> consumerApplications = consumerFacade.findApplications();
		// model.addAttribute("providerApplications", providerApplications);
		if (providerApplications != null && providerApplications.size() > 0) {
			applications.addAll(providerApplications);
		}
		if (consumerApplications != null && consumerApplications.size() > 0) {
			applications.addAll(consumerApplications);
		}

		model.addAttribute("applications", applications);
		model.addAttribute("providerAddresses", providerAddresses);
		model.addAttribute("consumerAddresses", consumerAddresses);
		model.addAttribute("addresses", addresses);
		return "addresses/list";
	}

	/**
	 * 查询指定应用的机器
	 *
	 * @param application
	 *            应用
	 * @param type
	 *            类型
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/application/{application}", method = RequestMethod.GET)
	public String addresses(
			@PathVariable(value = "application") String application,
			@RequestParam(value = "type", required = false) String type,
			Model model) {
		Set<String> addresses = new TreeSet<String>();
		Set<String> providerAddresses = Sets.newHashSet();
		Set<String> consumerAddresses = Sets.newHashSet();

		// 服务提供者
		if (!"consumer".equals(type)) {
			providerAddresses = providerFacade
					.findAddressesByApplication(application);
			addresses.addAll(providerAddresses);
		}

		// 服务消费者
		if (!"provider".equals(type)) {
			consumerAddresses = consumerFacade
					.findAddressesByApplication(application);
			addresses.addAll(consumerAddresses);
		}

		// 应用列表
		Set<String> applications = new TreeSet<String>();
		Set<String> providerApplications = providerFacade.findApplications();
		Set<String> consumerApplications = consumerFacade.findApplications();
		// model.addAttribute("providerApplications", providerApplications);
		if (providerApplications != null && providerApplications.size() > 0) {
			applications.addAll(providerApplications);
		}
		if (consumerApplications != null && consumerApplications.size() > 0) {
			applications.addAll(consumerApplications);
		}

		model.addAttribute("application", application);
		model.addAttribute("applications", applications);
		model.addAttribute("providerAddresses", providerAddresses);
		model.addAttribute("consumerAddresses", consumerAddresses);
		model.addAttribute("addresses", addresses);
		if (this.blockModel) {
			return "addresses/control/listControl";
		}
		return "addresses/list";
	}

	/**
	 * 设为内测
	 */
	@RequestMapping(value = "/in", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage changeInternal(
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "appName", required = false) String appName,
			Model model) {
		try {
			addressFacade.changeInternal(address, appName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 设为对外
	 */
	@RequestMapping(value = "/out", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage changeExternal(
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "appName", required = false) String appName,
			Model model) {
		try {
			addressFacade.changeExternal(address, appName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 启用
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage enable(
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "includeConsumer", required = false) Boolean includeConsumer,
			Model model) {
		try {
			if (includeConsumer == null) {
				includeConsumer = false;
			}
			// TODO delete : 下期可带端口
			address = Tool.getIP(address);
			addressFacade.enable(address, includeConsumer);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 启用
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage disable(
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "includeConsumer", required = false) Boolean includeConsumer,
			Model model) {
		try {
			if (includeConsumer == null) {
				includeConsumer = false;
			}
			// TODO delete : 下期可带端口
			address = Tool.getIP(address);
			addressFacade.disable(address, includeConsumer);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}
}
