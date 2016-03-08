package com.yeepay.g3.app.soa.center.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.dto.MethodInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.QueryTypeEnum;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.ServiceMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.ServiceQueryFacade;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * 服务查询（无管理功能）
 *
 * @author：wang.bao
 * @since：2014年8月15日 下午6:19:24
 * @version:
 */
@Controller
@RequestMapping("/services")
public class ServicesController extends BaseController {

	@Reference
	private ServiceQueryFacade serviceQueryFacade;

	@Reference
	private ServiceMgtFacade serviceMgtFacade;

	@Reference
	private AppMgtFacade appMgtFacade;

	@RequestMapping(value = {"", "/index"}, method = RequestMethod.GET)
	public String index() {
		return "services/index";
	}

	/**
	 * 检索服务
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String query(
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "appName", required = false) String appName,
			@RequestParam(value = "ownerName", required = false) String ownerName,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		ServiceQueryParam queryParam = new ServiceQueryParam();
		queryParam.setQueryInput(keyword);
		queryParam.setPageNo(pageNo);
		if (blockModel) {
			pageSize = Integer.MAX_VALUE;
		} else if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		queryParam.setPageSize(pageSize);
		queryParam.addExtParam("appName", appName);
		queryParam.addExtParam("ownerName", ownerName);
		String ret = null;
		if (StringUtils.equalsIgnoreCase(type, "app")) {
			queryParam.setQueryType(QueryTypeEnum.APP);
			ret = "applications";
		} else {
			queryParam.setQueryType(QueryTypeEnum.SERVICE);
			ret = "services";
		}
		PageList result = serviceQueryFacade.queryService(queryParam);
		model.addAttribute("result", result);
		if (blockModel) {
			ret += "/control";
		}
		return ret + "/query";
	}

	/**
	 * 服务详情
	 */
	@RequestMapping(value = "/detail/{serviceInterface:.+}", method = RequestMethod.GET)
	public String detail(
			@PathVariable(value = "serviceInterface") String serviceInterface,
			Model model) {
		ServiceInfoDTO serviceInfo = serviceQueryFacade
				.findService(serviceInterface);
		model.addAttribute("service", serviceInfo);
		Boolean isLack = Boolean.FALSE;
		List<String> lackInfo = new ArrayList<String>();
		if (StringUtils.isBlank(serviceInfo.getServiceName())) {
			lackInfo.add("服务名");
			isLack = Boolean.TRUE;
		}
		if (StringUtils.isBlank(serviceInfo.getServiceDesc())) {
			lackInfo.add("服务描述");
			isLack = Boolean.TRUE;
		}
		if (StringUtils.isBlank(serviceInfo.getServiceProtocol())) {
			lackInfo.add("支持协议");
			isLack = Boolean.TRUE;
		}
		model.addAttribute("isLack", isLack);
		if (CollectionUtils.isNotEmpty(lackInfo)) {
			model.addAttribute("lackInfo", StringUtils.join(lackInfo, "#"));
		}
		return "services/detail";
	}

	/**
	 * 更新服务信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveService(
			@RequestParam(value = "serviceId", required = true) Long serviceId,
			@RequestParam(value = "serviceName", required = true) String serviceName,
			@RequestParam(value = "serviceDesc", required = true) String serviceDesc,
			Model model) {
		try {
			ServiceInfoDTO serviceInfoDTO = new ServiceInfoDTO();
			serviceInfoDTO.setId(serviceId);
			serviceInfoDTO.setServiceName(serviceName);
			serviceInfoDTO.setServiceDesc(serviceDesc);
			serviceMgtFacade.updateService(serviceInfoDTO);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 标记方法已下线
	 */
	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage offlineService(
			@RequestParam(value = "serviceId", required = true) Long serviceId,
			Model model) {
		try {
			serviceMgtFacade.offlineService(serviceId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 启用服务
	 */
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage activeService(
			@RequestParam(value = "serviceId", required = true) Long serviceId,
			Model model) {
		try {
			serviceMgtFacade.activeService(serviceId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 禁用服务
	 */
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage forbidService(
			@RequestParam(value = "serviceId", required = true) Long serviceId,
			Model model) {
		try {
			serviceMgtFacade.forbidService(serviceId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除服务
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteService(
			@RequestParam(value = "serviceId", required = true) Long serviceId,
			Model model) {
		try {
			serviceMgtFacade.deleteService(serviceId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 更新服务信息
	 */
	@RequestMapping(value = "/method/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveMethod(
			@RequestParam(value = "methodId", required = true) Long methodId,
			@RequestParam(value = "methodDesc", required = true) String methodDesc,
			Model model) {
		try {
			MethodInfoDTO methodInfoDTO = new MethodInfoDTO();
			methodInfoDTO.setId(methodId);
			methodInfoDTO.setMethodDesc(methodDesc);
			serviceMgtFacade.updateMethod(methodInfoDTO);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 标记方法已下线
	 */
	@RequestMapping(value = "/method/offline", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage offlineMethod(
			@RequestParam(value = "methodId", required = true) Long methodId,
			Model model) {
		try {
			serviceMgtFacade.offlineMethod(methodId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除方法
	 */
	@RequestMapping(value = "/method/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteMethod(
			@RequestParam(value = "methodId", required = true) Long methodId,
			Model model) {
		try {
			serviceMgtFacade.deleteMethod(methodId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	@RequestMapping(value = "/{service}/routes", method = RequestMethod.GET)
	public String routes(@PathVariable(value = "service") String service,
						 RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("service", service);
		return "redirect:/routes";
	}
}
