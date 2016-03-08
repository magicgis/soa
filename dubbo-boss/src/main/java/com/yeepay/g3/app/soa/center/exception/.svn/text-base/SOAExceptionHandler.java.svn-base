/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.app.soa.center.exception;

import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.utils.common.exception.YeepayRuntimeException;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * <p>Title: SOA异常统一处理 Handler</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-5-16 3:10
 */
@ControllerAdvice
public class SOAExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String TEXT_PLAIN_UTF_8 = "text/plain; charset=UTF-8";

	private static final Logger LOGGER = LoggerFactory.getLogger(SOAExceptionHandler.class);

	@ExceptionHandler(ParseException.class)
	@ResponseBody
	public ResponseMessage handleParseException(ParseException e) {
		LOGGER.warn("解析异常: ", e);
		return new ResponseMessage(ResponseMessage.Status.ERROR, e.getMessage());
	}

	@ExceptionHandler(SOAException.class)
	@ResponseBody
	public ResponseMessage handleSOAException(SOAException e) {
		LOGGER.warn("系统内部异常", e);
		return new ResponseMessage(ResponseMessage.Status.ERROR, e.getMessage());
	}

	@ExceptionHandler(YeepayRuntimeException.class)
	public ModelAndView handleYeepayRuntimeException(HttpServletRequest request, YeepayRuntimeException e) {
		LOGGER.error("Requested URL=" + request.getRequestURL() + ", Exception Raised=" + e);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", e);
		modelAndView.addObject("url", request.getRequestURL());

		modelAndView.setViewName("common/error_exception");
		return modelAndView;
	}

	@ExceptionHandler(IOException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
	public void handleIOException(IOException e) {
		LOGGER.error("IOException handler executed", e);
		//returning 404 error code
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView handleUnKnownException(HttpServletRequest request, Exception e) {
		LOGGER.error("Requested URL=" + request.getRequestURL() + ", Exception Raised=" + e, e);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", e);
		modelAndView.addObject("url", request.getRequestURL());

		modelAndView.setViewName("common/error_exception");
		return modelAndView;
	}

}
