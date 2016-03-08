/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.g3.facade.soa.center.facade.IDCControlFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.web.IpUtils;

/**
 * 类名称: IDCCrossRateServlet <br>
 * 类描述: 控制IDC跨机房流量分配比率<br>
 *
 * @author: yufan.sheng wang.bao
 * @since: 15/4/7 上午11:32
 * @version: 1.0.0
 */
public class IDCCrossRateServlet extends SoaBaseServlet {
	private static final long serialVersionUID = 3742514494986722897L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		String rate = request.getParameter("rate");
		String operator = request.getParameter("operator");
		CheckUtils.notEmpty(rate, "rate");
		CheckUtils.notEmpty(operator, "operator");
		try {
			logger.saveToDB("change idc cross rate [" + rate + "%], operator ["
					+ operator + "], fromIP[" + IpUtils.getIpAddr(request)
					+ "]");
		} catch (Exception e) {
			logger.error("", e);
		}
		IDCControlFacade iDCControlFacade = (IDCControlFacade) context
				.getBean(IDCControlFacade.class);
		iDCControlFacade.changeCrossRate(Integer.parseInt(rate));
		writeResponse(response, "success");
	}
}