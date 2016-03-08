/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.param.AddressQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 *
 * @author：wang.bao
 * @since：2015年7月14日 上午10:35:58
 * @version:
 */
public class ProviderForbidMonitorServlet extends SoaBaseServlet {
	private static final long serialVersionUID = 5618558782889009090L;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		AddressFacade addressFacade = (AddressFacade) context
				.getBean(AddressFacade.class);
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setStatus(SoaStatusEnum.FORBID);
		queryParam.setPageSize(500);
		PageList addrs = addressFacade.queryAddress(queryParam);
		if (addrs != null && addrs.getData() != null) {
			logger.warn("forbid addresses : {0}", addrs.getData());
			writeResponse(response, "" + addrs.size());
		} else {
			writeResponse(response, "0");
		}
	}
}
