package com.yeepay.g3.app.soa.center.monitor.controller;

import java.util.List;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.facade.soa.center.dto.RegistryDTO;
import com.yeepay.g3.facade.soa.center.facade.RegistryMonitorFacade;
import com.yeepay.g3.facade.soa.center.param.RegisteredQueryParam;
import com.yeepay.g3.facade.soa.center.param.SubscribedQueryParam;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.soa.rpc.proxy.SoaServiceRepository;

/**
 * @author by menghao.chen
 */
@Controller
@RequestMapping(value = "/monitor/registries")
public class RegistriesMonitorController extends BaseController {
	@Reference
	private RegistryMonitorFacade registryMonitorFacade;

	@RequestMapping(value = "")
	public String queryRegistry(Model model) {

		try {
			test();
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<RegistryDTO> result = registryMonitorFacade.queryRegistry();
		int registeredCount = 0;
		int subscribedCount = 0;
		if (CollectionUtils.isNotEmpty(result)) {
			for (RegistryDTO item : result) {
				if (item.getRegisteredSize() != null) {
					registeredCount += item.getRegisteredSize();
				}
				if (item.getSubscribedSize() != null) {
					subscribedCount += item.getSubscribedSize();
				}
			}
		}
		model.addAttribute("result", result);
		model.addAttribute("registeredCount", registeredCount);
		model.addAttribute("subscribedCount", subscribedCount);
		return "monitor/registries";
	}

	private void test() throws CannotCompileException, NotFoundException {
		String classFullName = "com.yeepay.g3.facade.account.service.AccountManagementFacade";
		Object target = SoaServiceRepository.getService(classFullName);
		LOGGER.info("" + target);
	}

	@RequestMapping(value = "/registered")
	public String queryRegistered(
			@RequestParam(value = "registry") String registry,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		RegisteredQueryParam queryParam = new RegisteredQueryParam();
		queryParam.setRegistry(registry);
		queryParam.setAddress(address);
		queryParam.setPageSize(pageSize);
		queryParam.setPageNo(pageNo);
		PageList result = registryMonitorFacade.queryRegistered(queryParam);
		model.addAttribute("result", result);
		return "monitor/registered";
	}

	@RequestMapping(value = "/subscribed")
	public String querySubscribed(
			@RequestParam(value = "registry") String registry,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		SubscribedQueryParam queryParam = new SubscribedQueryParam();
		queryParam.setRegistry(registry);
		queryParam.setAddress(address);
		queryParam.setPageSize(pageSize);
		queryParam.setPageNo(pageNo);
		PageList result = registryMonitorFacade.querySubscribed(queryParam);
		model.addAttribute("result", result);
		return "monitor/subscribed";
	}
}
