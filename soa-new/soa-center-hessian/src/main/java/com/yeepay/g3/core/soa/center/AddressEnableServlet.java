/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.web.IpUtils;

/**
 * @author：wang.bao
 * @since：2015年1月5日 上午11:32:48
 * @version:
 */
public class AddressEnableServlet extends SoaBaseServlet {
	private static final long serialVersionUID = -6987078414364097926L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		String address = request.getParameter("address");
		String operator = request.getParameter("operator");
		CheckUtils.notEmpty(address, "address");
		CheckUtils.notEmpty(operator, "operator");
		try {
			logger.saveToDB("enable address [" + address + "], operator ["
					+ operator + "], fromIP[" + IpUtils.getIpAddr(request)
					+ "]");
		} catch (Exception e) {
			logger.error("", e);
		}
		AddressFacade addressFacade = (AddressFacade) context
				.getBean(AddressFacade.class);
		if (StringUtils.equals(address, "*")
				|| StringUtils.equals(address, "all")) {
			addressFacade.enableAll();
		} else {
			addressFacade.enable(address, true);
		}
		writeResponse(response, "success");
	}
}
