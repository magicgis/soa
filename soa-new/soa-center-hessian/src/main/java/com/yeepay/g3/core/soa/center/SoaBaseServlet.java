/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;

/**
 *
 * @author：wang.bao
 * @since：2015年7月14日 上午10:39:44
 * @version:
 */
public abstract class SoaBaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1141983468916800812L;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected WebApplicationContext context;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

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
			writeResponse(response, "error:" + e.getMessage());
		}
	}

	protected abstract void handle(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected void writeResponse(HttpServletResponse response, String content) {
		try {
			OutputStream out = response.getOutputStream();
			InputStream in = new ByteArrayInputStream(content.getBytes("UTF-8"));
			int b = -1;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Throwable e) {
			logger.error("", e);
		}
	}
}
