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
 * 修改机房IP规则
 *
 * @author：wang.bao
 * @since：2015年7月29日 下午2:07:39
 * @version:
 */
public class IDCIPRuleServlet extends SoaBaseServlet {
	private static final long serialVersionUID = -3988321544944951127L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		String idcName = request.getParameter("idcName");
		String ipRule = request.getParameter("ipRule");
		String operator = request.getParameter("operator");
		CheckUtils.notEmpty(idcName, "idcName");
		CheckUtils.notEmpty(ipRule, "ipRule");
		CheckUtils.notEmpty(operator, "operator");
		try {
			logger.saveToDB("change idc [" + idcName + "] ip rule to ["
					+ ipRule + "], operator [" + operator + "], fromIP["
					+ IpUtils.getIpAddr(request) + "]");
		} catch (Exception e) {
			logger.error("", e);
		}
		IDCControlFacade iDCControlFacade = (IDCControlFacade) context
				.getBean(IDCControlFacade.class);
		iDCControlFacade.changeIPRule(idcName, ipRule);
		writeResponse(response, "success");
	}
}
