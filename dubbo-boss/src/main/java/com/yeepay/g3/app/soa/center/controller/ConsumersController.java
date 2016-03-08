package com.yeepay.g3.app.soa.center.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.StringUtils;

/**
 * Title: 消费者控制 控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 17:12
 */
@Controller
@RequestMapping("/consumers")
public class ConsumersController extends BaseController {

	@Reference
	private ConsumerFacade consumerFacade;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(@RequestParam(value = "service", required = false) String service,
					   @RequestParam(value = "application", required = false) String application,
					   @RequestParam(value = "address", required = false) String address,
					   Model model) {
		List<Consumer> consumers;
		if (service != null && service.length() > 0) {
			consumers = consumerFacade.findWithOverrideByService(service);
		} else if (address != null && address.length() > 0) {
			consumers = consumerFacade.findWithOverrideByAddress(address);
		} else if (application != null && application.length() > 0) {
			consumers = consumerFacade.findWithOverrideByApplication(application);
		} else {
			consumers = consumerFacade.findAllWithOverride();
		}
		model.addAttribute("consumers", consumers);

		// 应用列表
		Set<String> consumerApplications = consumerFacade.findApplications();
		model.addAttribute("applications", consumerApplications);

		if (blockModel) {
			return "consumers/control/listControl";
		} else {
			return "consumers/list";
		}
	}

//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public String editForm(@RequestParam("id") Long id,
//						   Model model) {
//		model.addAttribute("consumer", consumerFacade.findConsumer(id));
//		return "consumers/edit";
//	}
//
//	@RequestMapping(value = "/edit", method = RequestMethod.POST)
//	public String edit(@RequestParam("service") String service,
//					   @RequestParam("address") String address,
//					   @RequestParam("allow") boolean allow,
//					   HttpSession session,
//					   RedirectAttributes redirectAttributes) {
//		try {
//			UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
////			consumerFacade.(service, address, allow, userDTO.getLoginName());
//			redirectAttributes.addFlashAttribute("info", "成功添加访问");
//		} catch (Exception e) {
//			throw new YeepayRuntimeException(e.getMessage());
//		}
//		return "redirect:/consumers/list";
//	}

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage method(@RequestParam(value = "service", required = false) String service) {
		Set<String> applications;
		if (org.apache.commons.lang3.StringUtils.isNotBlank(service)) {
			applications = consumerFacade.findApplicationsByServiceName(service);
		} else {
			applications = consumerFacade.findApplications();
		}
		return new ResponseMessage("applications", applications);
	}

	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id,
						 Model model) {
		Consumer consumer = consumerFacade.findConsumerDetail(id);
		model.addAttribute("consumer", consumer);
		model.addAttribute("paramMap", Tool.toParameterMap(consumer.getParameters()));
		return "consumers/detail";
	}

	@RequestMapping(value = "/detaillist", method = RequestMethod.GET)
	public String detailList(
			@RequestParam(value = "application", required = false) String application,
			@RequestParam(value = "service", required = false) String serviceName,
			@RequestParam(value = "address", required = false) String address,
			Model model) {
		List<Consumer> consumers = null;
		if (StringUtils.isNotBlank(application)) {
			consumers = this.consumerFacade.findByApplication(application);
		} else if (StringUtils.isNotBlank(serviceName)) {
			consumers = this.consumerFacade.findByService(serviceName);
		} else if (StringUtils.isNotBlank(address)) {
			consumers = this.consumerFacade.findByAddress(address);
		} else {
			consumers = new ArrayList<Consumer>(0);
		}
		model.addAttribute("consumers", consumers);
		return "consumers/detaillist";
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/shield", method = RequestMethod.POST)
	public void shield(@RequestParam("id") Long[] ids,
					   HttpServletRequest request,
					   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.shield(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/tolerant", method = RequestMethod.POST)
	public void tolerant(@RequestParam("id") Long[] ids,
						 HttpServletRequest request,
						 HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.tolerant(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/recover", method = RequestMethod.POST)
	public void recover(@RequestParam("id") Long[] ids,
						HttpServletRequest request,
						HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.recover(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/allshield", method = RequestMethod.POST)
	public void allshield(@RequestParam("service") String service,
						  HttpServletRequest request,
						  HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.allshield(service, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/alltolerant", method = RequestMethod.POST)
	public void alltolerant(@RequestParam("service") String service,
							HttpServletRequest request,
							HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.alltolerant(service, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/allrecover", method = RequestMethod.POST)
	public void allrecover(@RequestParam("service") String service,
						   HttpServletRequest request,
						   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.allrecover(service, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/allow", method = RequestMethod.POST)
	public void allow(@RequestParam("id") Long[] ids,
					  HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.allow(ids, userDTO.getLoginName());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	public void forbid(@RequestParam("id") Long[] ids,
					   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.forbid(ids, userDTO.getLoginName());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/onlyAllow", method = RequestMethod.POST)
	public void onlyAllow(@RequestParam("id") Long[] ids,
						  HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.onlyAllow(ids, userDTO.getLoginName());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/onlyForbid", method = RequestMethod.POST)
	public void onlyForbid(@RequestParam("id") Long[] ids,
						   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		consumerFacade.onlyForbid(ids, userDTO.getLoginName());
	}

}
