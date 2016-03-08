package com.yeepay.g3.app.soa.center.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Weight;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.facade.WeightFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.exception.YeepayRuntimeException;

/**
 * Title: 权重调节 控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 9:53
 */
@Controller
@RequestMapping("/weights")
public class WeightsController extends BaseController {

	@Reference
	private WeightFacade weightFacade;

	@Reference
	private ProviderFacade providerFacade;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(@RequestParam(value = "service", required = false) String service,
					   @RequestParam(value = "address", required = false) String address,
					   Model model) {
		model.addAttribute("serviceList", providerFacade.findServicesSorted());

		address = Tool.getIP(address);
		List<Weight> weights;
		if (service != null && service.length() > 0) {
			weights = weightFacade.findByService(service);
		} else if (address != null && address.length() > 0) {
			weights = weightFacade.findByAddress(address);
		} else {
			weights = weightFacade.findAll();
		}

		model.addAttribute("weights", weights);

		if (blockModel) {
			return "weights/control/listControl";
		} else {
			return "weights/list";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("weight", new Weight());
		model.addAttribute("method", "add");
		model.addAttribute("applications", providerFacade.findApplications());
		model.addAttribute("serviceList", providerFacade.findServicesSorted());
		return "weights/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam(value = "application", required = false) String application,
					  Weight weight,
					  HttpSession session,
					  RedirectAttributes redirectAttributes) {
		try {
			UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");

			// 如果指定应用但没有指定具体服务，则将访问控制应用到该应用的所有服务
			if (StringUtils.isNotBlank(application)
					&& StringUtils.isBlank(weight.getService())) {
				List<String> services = providerFacade.findServicesByApplication(application);
				for (String service : services) {
					weight.setService(service);
					weightFacade.create(weight, userDTO.getLoginName());
				}
			} else {
				weightFacade.create(weight, userDTO.getLoginName());
			}

			redirectAttributes.addFlashAttribute("info", "成功添加访问");
		} catch (Exception e) {
			throw new YeepayRuntimeException(e.getMessage());
		}
		return "redirect:/weights/list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(Model model) {
		model.addAttribute("method", "edit");
		model.addAttribute("serviceList", providerFacade.findServicesSorted());
		return "weights/add";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("weight") Weight weight,
					   HttpSession session,
					   RedirectAttributes redirectAttributes) {
		try {
			UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
			weightFacade.edit(weight, userDTO.getLoginName());
			redirectAttributes.addFlashAttribute("info", "成功添加访问");
		} catch (Exception e) {
			throw new YeepayRuntimeException(e.getMessage());
		}
		return "redirect:/weights/list";
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("id") Long[] ids,
					   HttpSession session) {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		weightFacade.delete(ids, userDTO.getLoginName());
	}

	@ModelAttribute
	public void getWeight(@RequestParam(value = "id", required = false) Long id, Model model) {
		if (null != id) {
			model.addAttribute("weight", weightFacade.findOne(id));
		}
	}

}
