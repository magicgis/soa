/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;
import com.yeepay.g3.utils.web.IpUtils;

/**
 *
 * @author：wang.bao
 * @since：2015年8月14日 下午6:58:50
 * @version:
 */
public class AddressRoleChangeServlet extends SoaBaseServlet {
	private static final long serialVersionUID = -5169020637496852315L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		String address = request.getParameter("address");
		String role = request.getParameter("role");
		String operator = request.getParameter("operator");
		CheckUtils.notEmpty(address, "address");
		CheckUtils.notEmpty(role, "role");
		CheckUtils.notEmpty(operator, "operator");
		try {
			logger.saveToDB("change address [" + address + "] role to [" + role
					+ "], operator [" + operator + "], fromIP ["
					+ IpUtils.getIpAddr(request) + "]");
		} catch (Exception e) {
			logger.error("", e);
		}
		AddressFacade addressFacade = (AddressFacade) context
				.getBean(AddressFacade.class);
		if (StringUtils.equalsIgnoreCase(AddressRoleEnum.OUT.name(), role)) {
			addressFacade.changeExternal(address, null);
		} else if (StringUtils
				.equalsIgnoreCase(AddressRoleEnum.IN.name(), role)) {
			addressFacade.changeInternal(address, null);
		} else {
			logger.warn("unsupport role : [" + role + "]");
		}
		writeResponse(response, "success");
	}
}
