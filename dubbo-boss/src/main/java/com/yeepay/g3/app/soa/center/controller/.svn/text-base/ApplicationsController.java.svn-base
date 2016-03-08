package com.yeepay.g3.app.soa.center.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.yeepay.g3.facade.soa.center.dto.AppDependencyDTO;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.facade.soa.center.enums.AppRoleEnum;
import com.yeepay.g3.facade.soa.center.enums.QueryTypeEnum;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.facade.OverrideFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.facade.ServiceQueryFacade;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.facade.soa.center.utils.OverrideUtils;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * Title: 应用查询控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/>
 * <br/>
 *
 * @author baitao.ji wang.bao
 * @version 0.1, 14-6-16 17:28
 */
@Controller
@RequestMapping("/applications")
public class ApplicationsController extends BaseController {
	@Reference
	private ProviderFacade providerFacade;

	@Reference
	private ConsumerFacade consumerFacade;

	@Reference
	private OverrideFacade overrideFacade;

	@Reference
	private ServiceQueryFacade serviceQueryFacade;

	@Reference
	private AppMgtFacade appMgtFacade;

	private static final String TEMPLATE_NODES = "#_nodes_#";
	private static final String TEMPLATE_LINKS = "#_links_#";
	private static final String ECHARTS_EMPTY = "{}";

	@RequestMapping(value = { "", "/query" }, method = RequestMethod.GET)
	public String query(
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "ownerName", required = false) String ownerName,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		ServiceQueryParam queryParam = new ServiceQueryParam();
		queryParam.setQueryInput(keyword);
		queryParam.setQueryType(QueryTypeEnum.APP);
		queryParam.setPageNo(pageNo);
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		queryParam.setPageSize(pageSize);
		queryParam.addExtParam("ownerName", ownerName);
		PageList result = serviceQueryFacade.queryService(queryParam);
		model.addAttribute("result", result);

		return "applications/query";
	}

	@RequestMapping(value = { "/dependency" }, method = RequestMethod.GET)
	public String dependency(Model model) {
		List<AppInfoDTO> apps = appMgtFacade.queryAllApp(false);
		int appCount = 0;
		int coreCount = 0;
		for (AppInfoDTO app : apps) {
			if (app.getRole() == AppRoleEnum.CONSUMER) {
				appCount++;
			} else {
				coreCount++;
			}
		}
		model.addAttribute("totalCount", apps.size());
		model.addAttribute("appCount", appCount);
		model.addAttribute("coreCount", coreCount);
		model.addAttribute("dependencyMeta", buidDependencyMeta(apps));
		return "applications/dependency";
	}

	/**
	 * 应用详情
	 */
	@RequestMapping(value = "/detail/{appName:.+}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "appName") String appName,
			Model model) {
		AppInfoDTO app = appMgtFacade.findApp(appName);
		model.addAttribute("app", app);
		model.addAttribute("dependencyMeta", buidDependencyMeta(app));

		Boolean isLack = Boolean.FALSE;
		List<String> lackInfo = new ArrayList<String>();
		if (StringUtils.isBlank(app.getAppTitle())) {
			lackInfo.add("应用名");
			isLack = Boolean.TRUE;
		}
		if (StringUtils.isBlank(app.getAppDesc())) {
			lackInfo.add("应用描述");
			isLack = Boolean.TRUE;
		}
		model.addAttribute("isLack", isLack);
		if (CollectionUtils.isNotEmpty(lackInfo)) {
			model.addAttribute("lackInfo", StringUtils.join(lackInfo, "#"));
		}
		return "applications/detail";
	}

	/**
	 * 更新应用信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveApp(
			@RequestParam(value = "appName", required = true) String appName,
			@RequestParam(value = "appTitle", required = true) String appTitle,
			@RequestParam(value = "javaDoc", required = false) String javaDoc,
			@RequestParam(value = "docPath", required = true) String docPath,
			@RequestParam(value = "appDesc", required = true) String appDesc,
			Model model) {
		try {
			AppInfoDTO appInfoDTO = new AppInfoDTO();
			appInfoDTO.setAppName(appName);
			appInfoDTO.setAppTitle(appTitle);
			appInfoDTO.setAppDesc(appDesc);
			appInfoDTO.setJavaDoc(javaDoc);
			appInfoDTO.setDocPath(docPath);
			appMgtFacade.saveApp(appInfoDTO);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除应用
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage delApp(
			@RequestParam(value = "appName", required = true) String appName,
			Model model) {
		try {
			appMgtFacade.deleteApp(appName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 下线应用
	 */
	@RequestMapping(value = "/offline", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage offline(
			@RequestParam(value = "appName", required = true) String appName,
			Model model) {
		try {
			appMgtFacade.offlineApp(appName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 禁用应用
	 */
	@RequestMapping(value = "/forbid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage forbid(
			@RequestParam(value = "appName", required = true) String appName,
			Model model) {
		try {
			appMgtFacade.forbidApp(appName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 禁用应用
	 */
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage active(
			@RequestParam(value = "appName", required = true) String appName,
			Model model) {
		try {
			appMgtFacade.activeApp(appName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 应用授权
	 */
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage auth(
			@RequestParam(value = "appName", required = true) String appName,
			@RequestParam(value = "toAppName", required = true) String toAppName,
			Model model) {
		try {
			appMgtFacade.authApp(appName, toAppName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 取消应用授权
	 */
	@RequestMapping(value = "/unauth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage unauth(
			@RequestParam(value = "appName", required = true) String appName,
			@RequestParam(value = "toAppName", required = true) String toAppName,
			Model model) {
		try {
			appMgtFacade.unAuthApp(appName, toAppName);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	@RequestMapping(value = "/moveJavadoc", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage moveJavadoc(
			@RequestParam(value = "oldJavadocPath", required = false) String oldJavadocPath,
			@RequestParam(value = "newJavadocPath", required = true) String newJavadocPath,
			Model model) {
		try {
			appMgtFacade.moveJavadoc(oldJavadocPath, newJavadocPath);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	private String buidDependencyMeta(AppInfoDTO app) {
		if (app == null
				|| StringUtils.isBlank(viewLogicHelper
						.getSingleAppDependencyTpl())) {
			return ECHARTS_EMPTY;
		}
		try {
			StringBuilder nodes = new StringBuilder();
			StringBuilder links = new StringBuilder();
			nodes.append("{category:2, name: '" + app.getAppName()
					+ "', value : 30}");
			for (AppDependencyDTO ref : app.getDepByAppList()) {
				nodes.append(",\n{category:0, name: '" + ref.getAppName()
						+ "', value : 10}");
				links.append(",\n{source : '" + ref.getAppName()
						+ "', target : '" + ref.getDepAppName()
						+ "', weight : 1}");
			}
			for (AppDependencyDTO dep : app.getDepAppList()) {
				nodes.append(",\n{category:1, name: '" + dep.getDepAppName()
						+ "', value : 20}");
				links.append(",\n{source : '" + dep.getAppName()
						+ "', target : '" + dep.getDepAppName()
						+ "', weight : 1}");
			}

			String linkStr = links.toString();
			if (linkStr.length() > 0) {
				linkStr = links.substring(1);
			}
			return viewLogicHelper.getSingleAppDependencyTpl()
					.replace(TEMPLATE_NODES, nodes.toString())
					.replace(TEMPLATE_LINKS, linkStr);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return ECHARTS_EMPTY;
	}

	private String buidDependencyMeta(List<AppInfoDTO> apps) {
		if (apps == null
				|| apps.isEmpty()
				|| StringUtils
						.isBlank(viewLogicHelper.getAllAppDependencyTpl())) {
			return ECHARTS_EMPTY;
		}
		try {
			StringBuilder nodes = new StringBuilder();
			StringBuilder links = new StringBuilder();
			for (AppInfoDTO app : apps) {
				// 默认提供者
				int category = 1;
				if (app.getRole() == AppRoleEnum.BOTH) {
					// both
					category = 2;
				} else if (app.getRole() == AppRoleEnum.CONSUMER) {
					// 消费者
					category = 0;
				}
				nodes.append(",\n{category:"
						+ category
						+ ", name: '"
						+ app.getAppName()
						+ "', value : "
						+ ((app.getDepAppCount() + app.getDepByAppCount() + 1) * 10)
						+ "}");
				for (AppDependencyDTO dep : app.getDepAppList()) {
					if (StringUtils.equals(dep.getDepAppName(),
							"soa-center-hessian")
							&& !StringUtils.equals(dep.getAppName(),
									"dubbo-boss")) {
						continue;
					}
					links.append(",\n{source : '" + dep.getAppName()
							+ "', target : '" + dep.getDepAppName()
							+ "', weight : " + getWeight(dep) + "}");
				}
			}
			String nodeStr = nodes.toString().substring(1);
			String linkStr = links.toString();
			if (linkStr.length() > 0) {
				linkStr = links.substring(1);
			}
			return viewLogicHelper.getAllAppDependencyTpl()
					.replace(TEMPLATE_NODES, nodeStr)
					.replace(TEMPLATE_LINKS, linkStr);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return ECHARTS_EMPTY;
	}

	private int getWeight(AppDependencyDTO dep) {
		String prefix = dep.getAppName().split("-")[0];
		String prefixDep = dep.getDepAppName().split("-")[0];
		return StringUtils.equals(prefix, prefixDep) ? 20 : 10;
	}

	/**
	 * 查询应用
	 *
	 * @param service
	 *            服务名
	 * @param application
	 *            应用名
	 * @param address
	 *            机器名
	 * @param keyword
	 *            关键词(小写字母)
	 * @param type
	 *            角色
	 * @return
	 */
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "service", required = false) String service,
			@RequestParam(value = "application", required = false) String application,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "type", required = false) String type,
			Model model) {
		Set<String> applications = new TreeSet<String>();
		Set<String> providerApplications = Sets.newHashSet();
		Set<String> consumerApplications = Sets.newHashSet();
		if (StringUtils.isNotBlank(service)) {
			// 服务提供者
			if (!"consumer".equals(type)) {
				providerApplications = providerFacade
						.findApplicationsByServiceName(service);
				if (providerApplications != null
						&& providerApplications.size() > 0) {
					applications.addAll(providerApplications);
				}
				model.addAttribute("providerApplications", providerApplications);
			}

			// 服务消费者
			if (!"provider".equals(type)) {
				consumerApplications = consumerFacade
						.findApplicationsByServiceName(service);
				if (consumerApplications != null
						&& consumerApplications.size() > 0) {
					applications.addAll(consumerApplications);
				}
				model.addAttribute("consumerApplications", consumerApplications);
			}

			model.addAttribute("applications", applications);

			if (null != service && service.length() > 0) {
				List<Override> overrides = overrideFacade
						.findByService(service);
				Map<String, List<Override>> application2Overrides = new HashMap<String, List<Override>>();
				if (overrides.size() > 0 && applications.size() > 0) {
					for (String a : applications) {
						List<Override> appOverrides = new ArrayList<Override>();
						for (Override override : overrides) {
							if (override.isMatch(service, null, a)) {
								appOverrides.add(override);
							}
						}
						Collections.sort(appOverrides,
								OverrideUtils.OVERRIDE_COMPARATOR);
						application2Overrides.put(a, appOverrides);
					}
				}
				model.addAttribute("overrides", application2Overrides);
			}
		} else {
			// 服务提供者
			if (!"consumer".equals(type)) {
				providerApplications = providerFacade.findApplications();
				if (providerApplications != null
						&& providerApplications.size() > 0) {
					applications.addAll(providerApplications);
				}
			}

			// 服务消费者
			if (!"provider".equals(type)) {
				consumerApplications = consumerFacade.findApplications();
				if (consumerApplications != null
						&& consumerApplications.size() > 0) {
					applications.addAll(consumerApplications);
				}
			}

			Set<String> newList = new HashSet<String>();
			Set<String> newProviders = new HashSet<String>();
			Set<String> newConsumers = new HashSet<String>();

			if (StringUtils.isNotEmpty(keyword) && !"*".equals(keyword)) {
				keyword = keyword.toLowerCase();
				for (String o : applications) {
					if (o.toLowerCase().indexOf(keyword) != -1) {
						newList.add(o);
					}
				}
				for (String o : providerApplications) {
					if (o.toLowerCase().indexOf(keyword) != -1) {
						newProviders.add(o);
					}
				}
				for (String o : consumerApplications) {
					if (o.toLowerCase().indexOf(keyword) != -1) {
						newConsumers.add(o);
					}
				}
				model.addAttribute("applications", newList);
				model.addAttribute("providerApplications", newProviders);
				model.addAttribute("consumerApplications", newConsumers);
			} else {
				model.addAttribute("applications", applications);
				model.addAttribute("providerApplications", providerApplications);
				model.addAttribute("consumerApplications", consumerApplications);
			}
		}
		return "applications/list";
	}

	@RequestMapping(value = "/application/{application}/services", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage method(
			@PathVariable(value = "application") String application) {
		List<String> services = providerFacade
				.findServicesByApplication(application);
		return new ResponseMessage("services", services);
	}

}
