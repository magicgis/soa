/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.g3.facade.soa.center.facade.AddressFacade;

/**
 *
 * @author：wang.bao
 * @since：2015年7月14日 上午10:35:58
 * @version:
 */
public class NoProviderMonitorServlet extends SoaBaseServlet {
	private static final long serialVersionUID = 5618558782889009090L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		AddressFacade addressFacade = (AddressFacade) context
				.getBean(AddressFacade.class);
		Set<String> apps = addressFacade.findNoProvoiderApps();
		if (apps == null || apps.isEmpty()) {
			writeResponse(response, "none");
		} else {
			writeResponse(response, apps.toString());
		}
	}
}
