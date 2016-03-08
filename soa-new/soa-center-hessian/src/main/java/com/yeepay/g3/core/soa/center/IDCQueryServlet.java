/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yeepay.g3.facade.soa.center.facade.IDCControlFacade;
import com.yeepay.g3.utils.common.json.JSONUtils;
import com.yeepay.g3.utils.web.IpUtils;

/**
 * 启用某个机房
 *
 * @author：wang.bao
 * @since：2015年7月29日 下午2:07:39
 * @version:
 */
public class IDCQueryServlet extends SoaBaseServlet {
	private static final long serialVersionUID = 942688504063460909L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			ServletContext servletContext = getServletContext();
			context = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			handle(request, response);
		} catch (Throwable e) {
			logger.error("", e);
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("errMsg", e.getMessage());
			writeResponse(response, JSONUtils.toJsonString(info));
		}
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) {
		logger.info("query all idc info, fromIP[" + IpUtils.getIpAddr(request)
				+ "]");
		IDCControlFacade iDCControlFacade = (IDCControlFacade) context
				.getBean(IDCControlFacade.class);
		Map<String, Object> info = iDCControlFacade.queryInfo();
		writeResponse(response, JSONUtils.toJsonString(info));
	}

	protected void writeResponse(HttpServletResponse response, String content) {
		response.setContentType("application/json");
		super.writeResponse(response, content);
	}
}
