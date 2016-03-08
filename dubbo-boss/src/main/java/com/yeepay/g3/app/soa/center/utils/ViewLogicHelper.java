/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.app.soa.center.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.yeepay.g3.app.frame.ControllerContext;
import com.yeepay.g3.facade.employee.dto.sso.DataDictDTO;
import com.yeepay.g3.facade.employee.facade.SecurityConfigFacade;
import com.yeepay.g3.facade.employee.role.dto.RoleDTO;
import com.yeepay.g3.facade.employee.user.dto.UserDTO;
import com.yeepay.g3.facade.soa.center.dto.MonitorChartDataItemDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.common.DateUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.config.ConfigParam;
import com.yeepay.g3.utils.config.ConfigurationUtils;
import com.yeepay.g3.utils.rmi.RemoteServiceFactory;

/**
 * Title: 页面展示帮助接口<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/>
 * <br/>
 *
 * @author baitao.ji wang.bao
 * @version 0.1, 14-5-22 11:18
 */
@Component
public final class ViewLogicHelper {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ViewLogicHelper.class);

	private static final String IS_SOA_ADMIN = "_IS_SOA_ADMIN_";
	private static final String SOA_ADMIN_ROLEID = "soa.admin.roleid";

	private SecurityConfigFacade securityConfigFacade = RemoteServiceFactory
			.getService(SecurityConfigFacade.class);

	private String singleAppDependencyTpl;

	private String allAppDependencyTpl;

	@PostConstruct
	public void init() {
		this.singleAppDependencyTpl = this
				.readClasspathFile("template/singleAppDependency.tpl");
		this.allAppDependencyTpl = this
				.readClasspathFile("template/allAppDependency.tpl");
	}

	private String readClasspathFile(String uri) {
		String content = null;
		try {
			InputStream is = this.getClass().getClassLoader()
					.getResourceAsStream(uri);
			InputStreamReader reader = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			StringBuilder tpl = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				tpl.append(line + "\n");
				line = br.readLine();
			}
			return tpl.toString();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return content;
	}

	public String getSingleAppDependencyTpl() {
		return singleAppDependencyTpl;
	}

	public String getAllAppDependencyTpl() {
		return allAppDependencyTpl;
	}

	public String getStatusDesc(String status) {
		try {
			return SoaStatusEnum.valueOf(status).getDesc();
		} catch (Exception e) {
			return status;
		}
	}

	public boolean isSoaAdmin() {
		HttpServletRequest request = ControllerContext.getContext()
				.getRequest();
		if (request == null) {
			return false;
		}
		Boolean isSoaAdmin = (Boolean) request.getSession().getAttribute(
				IS_SOA_ADMIN);
		if (isSoaAdmin != null) {
			return isSoaAdmin;
		}
		Long roleId = getSoaAdminRoleId();
		if (roleId == null) {
			return false;
		}
		UserDTO user = (UserDTO) request.getSession().getAttribute(
				DataDictDTO.SESSION_USERINFO);
		if (user == null) {
			request.getSession().setAttribute(IS_SOA_ADMIN, false);
			return false;
		}
		List<RoleDTO> roles = securityConfigFacade.queryRolesByUser(user
				.getUserId());
		for (RoleDTO role : roles) {
			if (role.getRoleId().equals(roleId)) {
				request.getSession().setAttribute(IS_SOA_ADMIN, true);
				return true;
			}
		}
		request.getSession().setAttribute(IS_SOA_ADMIN, false);
		return false;
	}

	@SuppressWarnings("unchecked")
	public static Long getSoaAdminRoleId() {
		try {
			ConfigParam<Long> config = ConfigurationUtils
					.getAppConfigParam(SOA_ADMIN_ROLEID);
			if (config != null && config.getValue() != null) {
				return config.getValue();
			}
		} catch (Exception e) {
		}
		return null;
	}

	public String breakLine(String content) {
		return breakLine(content, "\n");
	}

	public String breakLine(String content, String spliter) {
		if (StringUtils.isNotBlank(content)) {
			return content.replace(spliter, "<br/>");
		}
		return StringUtils.EMPTY;
	}

	public String[] split(String content, String spliter) {
		if (StringUtils.isNotBlank(content)) {
			return content.split(spliter);
		}
		return new String[] { content };
	}

	/**
	 * javadoc根目录
	 */
	@SuppressWarnings("unchecked")
	public String getJavadocCtxPath() {
		String retValue = "";
		try {
			ConfigParam<String> config = ConfigurationUtils
					.getSysConfigParam("soa.javadoc.ctxpath");
			if (config != null && StringUtils.isNotBlank(config.getValue())) {
				retValue = config.getValue();
			}
		} catch (Exception e) {
		}
		if (!StringUtils.endsWith(retValue, "/")) {
			retValue = StringUtils.defaultIfNull(retValue) + "/";
		}
		return retValue;
	}

	public Set<Date> mergeDateAndSort(List<MonitorChartDataItemDTO> list1,
			List<MonitorChartDataItemDTO> list2) {
		SortedSet<Date> mergeResult = Sets.newTreeSet();
		if (null != list1 && null != list2) {
			int i = 0, j = 0;
			for (; i < list1.size() && j < list2.size();) {
				int diff = DateUtils.compareDate(list1.get(i).getLabel(), list2
						.get(j).getLabel(), Calendar.MILLISECOND);
				if (diff < 0) {
					mergeResult.add(list1.get(i).getLabel());
					i++;
				} else if (diff > 0) {
					mergeResult.add(list2.get(j).getLabel());
					j++;
				} else {
					mergeResult.add(list1.get(i).getLabel());
					i++;
					j++;
				}
			}
			for (; i < list1.size(); i++) {
				mergeResult.add(list1.get(i).getLabel());
			}
			for (; j < list2.size(); j++) {
				mergeResult.add(list2.get(j).getLabel());
			}
		}
		return mergeResult;
	}

	public String handleThreadPoolInfo(String info) {
		if (StringUtils.isBlank(info)) {
			return info;
		}
		return info.replace(";", ";<br>");
	}

}
