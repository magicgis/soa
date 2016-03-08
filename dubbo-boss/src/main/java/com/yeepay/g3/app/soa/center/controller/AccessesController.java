package com.yeepay.g3.app.soa.center.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Access;
import com.yeepay.g3.facade.soa.center.facade.AccessFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.exception.YeepayRuntimeException;

/**
 * Title: 访问控制 控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-18 10:04
 */
@Controller
@RequestMapping("/accesses")
public class AccessesController extends BaseController {

	@Reference
	private AccessFacade accessFacade;

	@Reference
	private ProviderFacade providerFacade;

	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(@RequestParam(value = "service", required = false) String service,
					   @RequestParam(value = "address", required = false) String address,
					   Model model) throws ParseException {
		model.addAttribute("serviceList", providerFacade.findServicesSorted());

		address = Tool.getIP(address);
		List<Access> accesses;
		if (service != null && service.length() > 0) {
			accesses = accessFacade.findByService(service);
		} else if (address != null && address.length() > 0) {
			accesses = accessFacade.findByAddress(address);
		} else {
			accesses = accessFacade.findAll();
		}
		model.addAttribute("accesses", accesses);

		if (blockModel) {
			return "accesses/control/listControl";
		} else {
			return "accesses/list";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("access", new Access());
		model.addAttribute("applications", providerFacade.findApplications());
		model.addAttribute("serviceList", providerFacade.findServicesSorted());
		return "accesses/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam(value = "application", required = false) String application,
					  @RequestParam(value = "service", required = false) String service,
					  @RequestParam("address") String address,
					  @RequestParam("allow") boolean allow,
					  HttpSession session,
					  RedirectAttributes redirectAttributes) {
		try {
			UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");

			// 如果指定应用但没有指定具体服务，则将访问控制应用到该应用的所有服务
			if (StringUtils.isNotBlank(application)
					&& StringUtils.isBlank(service)) {
				List<String> services = providerFacade.findServicesByApplication(application);
				for (String item : services) {
					accessFacade.create(item, address, allow, userDTO.getLoginName());
				}
			} else {
				accessFacade.create(service, address, allow, userDTO.getLoginName());
			}
			redirectAttributes.addFlashAttribute("info", "成功添加访问");
		} catch (Exception e) {
			throw new YeepayRuntimeException(e.getMessage());
		}
		return "redirect:/accesses/list";
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("service") List<String> service,
					   @RequestParam("address") List<String> address,
					   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		accessFacade.deleteByServiceAndAddress(service, address, userDTO.getLoginName());
	}

}
