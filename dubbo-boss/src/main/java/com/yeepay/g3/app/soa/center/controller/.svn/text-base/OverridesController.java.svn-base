package com.yeepay.g3.app.soa.center.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Sets;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.facade.OverrideFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * Title: 动态配置查询控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-30 14:57
 */
@Controller
@RequestMapping("/overrides")
public class OverridesController extends BaseController {

	@Reference
	private ProviderFacade providerFacade;

	@Reference
	private ConsumerFacade consumerFacade;

	@Reference
	private OverrideFacade overrideFacade;

	/**
	 * 查询动态配置
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String overrides(
			@RequestParam(value = "pageNo", required = false, defaultValue="1") Integer pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue="50") Integer pageSize,
			Model model) {
		PageList result = overrideFacade.findAll(pageNo, pageSize);
		Set<?> overrides = Sets.newHashSet(result.getData());
		// 服务提供者
		Set<String> providerApplications = new TreeSet<String>();
		providerApplications.addAll(providerFacade.findApplications());
		model.addAttribute("providerApplications", providerApplications);

		// 应用列表
		Set<String> applications = new TreeSet<String>();
		if (providerApplications.size() > 0) {
			applications.addAll(providerApplications);
		}

		model.addAttribute("overrides", overrides);
		model.addAttribute("applications", applications);
		model.addAttribute("result", result);
		return "overrides/list";
	}

	/**
	 * 查询指定应用的动态配置
	 *
	 * @param application 应用
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/application/{application}", method = RequestMethod.GET)
	public String overrides(
			@PathVariable(value = "application") String application, Model model) {
		Set<Override> overrides = Sets.newHashSet(overrideFacade
				.findByApplication(application));

		// 服务提供者
		Set<String> providerApplications = new TreeSet<String>();
		providerApplications.addAll(providerFacade.findApplications());
		model.addAttribute("providerApplications", providerApplications);
		overrideFacade.findByApplication(application);

		// 应用列表
		Set<String> applications = new TreeSet<String>();
		if (providerApplications.size() > 0) {
			applications.addAll(providerApplications);
		}

		model.addAttribute("application", application);
		model.addAttribute("applications", applications);
		model.addAttribute("overrides", overrides);
		return "overrides/list";
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(@RequestParam("id") Long id,
					   Model model) throws ParseException {
		Override override = overrideFacade.findById(id);

		Map<String, String> parameters = parseQueryString(override.getParams());
		if (parameters.get(DEFAULT_MOCK_JSON_KEY) != null) {
			String mock = URL.decode(parameters.get(DEFAULT_MOCK_JSON_KEY));
			String[] tokens = parseMock(mock);
			model.addAttribute(FORM_DEFAULT_MOCK_METHOD_FORCE, tokens[0]);
			model.addAttribute(FORM_DEFAULT_MOCK_METHOD_JSON, tokens[1]);
			parameters.remove(DEFAULT_MOCK_JSON_KEY);
		}

		Map<String, String> method2Force = new LinkedHashMap<String, String>();
		Map<String, String> method2Json = new LinkedHashMap<String, String>();
		for (Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator(); iterator.hasNext(); ) {
			Map.Entry<String, String> e = iterator.next();
			String key = e.getKey();
			if (key.endsWith(MOCK_JSON_KEY_POSTFIX)) {
				String m = key.substring(0, key.length() - MOCK_JSON_KEY_POSTFIX.length());
				parseMock(m, e.getValue(), method2Force, method2Json);
				iterator.remove();
			}
		}

		model.addAttribute("methodForces", method2Force);
		model.addAttribute("methodJsons", method2Json);
		model.addAttribute("parameters", parameters);
		model.addAttribute("override", override);
		return "overrides/detail";
	}

	static final Pattern AND = Pattern.compile("\\&");
	static final Pattern EQUAL = Pattern.compile("([^=\\s]*)\\s*=\\s*(\\S*)");

	static Map<String, String> parseQueryString(String query) {
		HashMap<String, String> ret = new HashMap<String, String>();
		if (query == null || (query = query.trim()).length() == 0) return ret;

		String[] kvs = AND.split(query);
		for (String kv : kvs) {
			Matcher matcher = EQUAL.matcher(kv);
			if (!matcher.matches()) continue;
			String key = matcher.group(1);
			String value = matcher.group(2);
			ret.put(key, value);
		}
		return ret;
	}

	private void parseMock(String m, String mock, Map<String, String> method2Force, Map<String, String> method2Json) {
		String[] tokens = parseMock(mock);
		method2Force.put(m, tokens[0]);
		method2Json.put(m, tokens[1]);
	}

	private String[] parseMock(String mock) {
		mock = URL.decode(mock);
		String force;
		if (mock.startsWith("force:")) {
			force = "force";
			mock = mock.substring("force:".length());
		} else if (mock.startsWith("fail:")) {
			force = "fail";
			mock = mock.substring("fail:".length());
		} else {
			force = "fail";
		}
		String[] tokens = new String[2];
		tokens[0] = force;
		tokens[1] = mock;
		return tokens;
	}

	static final String DEFAULT_MOCK_JSON_KEY = "mock";
	static final String MOCK_JSON_KEY_POSTFIX = ".mock";

	// FORM KEY

	static final String FORM_OVERRIDE_METHOD = "overrideMethod";
	static final String FORM_OVERRIDE_KEY = "overrideKey";
	static final String FORM_OVERRIDE_VALUE = "overrideValue";

	static final String FORM_DEFAULT_MOCK_METHOD_FORCE = "mockDefaultMethodForce";
	static final String FORM_DEFAULT_MOCK_METHOD_JSON = "mockDefaultMethodJson";

	static final String FORM_ORIGINAL_METHOD_NAME = "mockMethodName";
	static final String FORM_ORIGINAL_METHOD_FORCE = "mockMethodForce";
	static final String FORM_ORIGINAL_METHOD_JSON = "mockMethodJson";

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute("override", new Override());
		model.addAttribute("method", "add");
		model.addAttribute("applications", providerFacade.findApplications());
		model.addAttribute("serviceList", providerFacade.findServicesSorted());
		model.addAttribute("consumerApplications", null);
		return "overrides/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam(value = "providerApplication", required = false) String providerApplication,
					  Override override,
					  RedirectAttributes redirectAttributes,
					  HttpServletRequest request) {
		catchParams(override, request.getParameterMap());

		// 如果指定应用但没有指定具体服务，则将动态配置应用到该应用的所有服务
		if (StringUtils.isNotBlank(providerApplication)
				&& StringUtils.isBlank(override.getService())) {
			List<String> services = providerFacade.findServicesByApplication(providerApplication);
			for (String service : services) {
				override.setService(service);
				overrideFacade.saveOverride(override);
			}
		} else {
			overrideFacade.saveOverride(override);
		}
		redirectAttributes.addFlashAttribute("info", "成功添加路由规则");
		return "redirect:/overrides/list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editForm(@RequestParam("id") Long id,
						   Model model) throws ParseException {
		Override override = overrideFacade.findById(id);

		Map<String, String> parameters = parseQueryString(override.getParams());
		if (parameters.get(DEFAULT_MOCK_JSON_KEY) != null) {
			String mock = URL.decode(parameters.get(DEFAULT_MOCK_JSON_KEY));
			String[] tokens = parseMock(mock);
			model.addAttribute(FORM_DEFAULT_MOCK_METHOD_FORCE, tokens[0]);
			model.addAttribute(FORM_DEFAULT_MOCK_METHOD_JSON, tokens[1]);
			parameters.remove(DEFAULT_MOCK_JSON_KEY);
		}

		Map<String, String> method2Force = new LinkedHashMap<String, String>();
		Map<String, String> method2Json = new LinkedHashMap<String, String>();
		for (Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator(); iterator.hasNext(); ) {
			Map.Entry<String, String> e = iterator.next();
			String key = e.getKey();
			if (key.endsWith(MOCK_JSON_KEY_POSTFIX)) {
				String m = key.substring(0, key.length() - MOCK_JSON_KEY_POSTFIX.length());
				parseMock(m, e.getValue(), method2Force, method2Json);
				iterator.remove();
			}
		}

		Set<String> methods = providerFacade.findMethodsByService(override.getService());
		model.addAttribute("methods", methods);

		model.addAttribute("methodForces", method2Force);
		model.addAttribute("methodJsons", method2Json);
		model.addAttribute("parameters", parameters);
		model.addAttribute("override", override);
		model.addAttribute("method", "edit");
		model.addAttribute("consumerApplications", consumerFacade.findApplicationsByServiceName(override.getService()));
		return "overrides/add";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("override") Override override,
					   RedirectAttributes redirectAttributes,
					   HttpServletRequest request) {
		catchParams(override, request.getParameterMap());
		overrideFacade.updateOverride(override);
		redirectAttributes.addFlashAttribute("info", "成功修改路由规则");
		return "redirect:/overrides/list";
	}

	private void catchParams(Override override, Map<String, Object> context) {
		StringBuilder paramters = new StringBuilder();
		String defaultMockMethodForce = (String) ((Object[]) context.get(FORM_DEFAULT_MOCK_METHOD_FORCE))[0];
		String defaultMockMethodJson = (String) ((Object[]) context.get(FORM_DEFAULT_MOCK_METHOD_JSON))[0];
		if (defaultMockMethodJson != null && defaultMockMethodJson.trim().length() > 0) {
			paramters.append("mock=").append(URL.encode(defaultMockMethodForce + ":" + defaultMockMethodJson.trim()));
		}

		// 服务降级
		Object[] mockMethodName = (Object[]) context.get(FORM_ORIGINAL_METHOD_NAME);
		Object[] mockMethodForce = (Object[]) context.get(FORM_ORIGINAL_METHOD_FORCE);
		Object[] mockMethodJson = (Object[]) context.get(FORM_ORIGINAL_METHOD_JSON);
		if (mockMethodName.length != mockMethodForce.length
				|| mockMethodName.length != mockMethodJson.length) {
			throw SOAException.OVERRIDE_LENGTH_NOT_EQUALS_EXCEPTION
					.newInstance("动态配置信息的key、value个数不匹配");
		}
		for (int i = 0; i < mockMethodName.length; ++i) {
			paramters.append("&").append(mockMethodName[i]).append(MOCK_JSON_KEY_POSTFIX)
					.append("=").append(URL.encode((mockMethodForce[i] + ":" + mockMethodJson[i])));
		}

		// 动态配置
		Object[] overrideMethod = (Object[]) context.get(FORM_OVERRIDE_METHOD);
		Object[] overrideKey = (Object[]) context.get(FORM_OVERRIDE_KEY);
		Object[] overrideValue = (Object[]) context.get(FORM_OVERRIDE_VALUE);
		if (overrideKey.length != overrideValue.length
				|| overrideKey.length != overrideMethod.length) {
			throw SOAException.OVERRIDE_LENGTH_NOT_EQUALS_EXCEPTION
					.newInstance("动态配置信息的key、value个数不匹配");
		}
		for (int i = 0; i < overrideKey.length; ++i) {
			String method = (String) overrideMethod[i];
			paramters.append("&");
			if (StringUtils.isNotBlank(method)) {
				paramters.append(method).append(".");
			}
			paramters.append(overrideKey[i]).append("=").append(URL.encode((String) overrideValue[i]));
		}

		override.setParams(paramters.toString());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("id") Long[] ids) {
		overrideFacade.deleteOverride(ids);
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	public void forbid(@RequestParam("id") Long[] ids,
					   HttpServletRequest request,
					   HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		overrideFacade.forbid(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/allow", method = RequestMethod.POST)
	public void allow(@RequestParam("id") Long[] ids,
					  HttpServletRequest request,
					  HttpSession session) throws ParseException {
		UserDTO userDTO = (UserDTO) session.getAttribute("yeepay_session_user");
		overrideFacade.allow(ids, userDTO.getLoginName(), request.getRemoteAddr());
	}

}
