/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.facade.DeployRecoverFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.web.IpUtils;

/**
 * @author：wang.bao
 * @since：2015年1月5日 上午11:32:48
 * @version:
 */
public class AddressDisableServlet extends SoaBaseServlet {
	private static final long serialVersionUID = -6987078414364097926L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		String address = request.getParameter("address");
		String operator = request.getParameter("operator");
		CheckUtils.notEmpty(address, "address");
		CheckUtils.notEmpty(operator, "operator");
		try {
			logger.saveToDB("disable address [" + address + "], operator ["
					+ operator + "], fromIP[" + IpUtils.getIpAddr(request)
					+ "]");
			// 此接口一般只暴露给应用发布工具
			DeployRecoverFacade deployRecoverFacade = (DeployRecoverFacade) context
					.getBean(DeployRecoverFacade.class);
			String reqAddress = IpUtils.getIpAddr(request);
			if (StringUtils.isBlank(reqAddress) || reqAddress.startsWith("0:0")) {
				reqAddress = NetUtils.getLocalHost();
			} else if (reqAddress.length() > 15) {
				reqAddress = reqAddress.substring(0, 15);
			}
			deployRecoverFacade.deploy(address, operator, reqAddress);
		} catch (Exception e) {
			logger.error("", e);
		}
		AddressFacade addressFacade = (AddressFacade) context
				.getBean(AddressFacade.class);
		addressFacade.disable(address, false);

		writeResponse(response, "success");
	}
}
