/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.app.soa.center.controller;

import com.yeepay.g3.app.frame.utils.PermissionUtils;
import com.yeepay.g3.app.soa.center.utils.ViewLogicHelper;
import com.yeepay.g3.utils.common.MessageFormater;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.exception.YeepayBizException;
import com.yeepay.g3.utils.common.exception.YeepayRuntimeException;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.config.TextResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

import java.net.SocketTimeoutException;
import java.util.regex.Pattern;

/**
 * Title: 基础控制器<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/>
 * <br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-5-22 9:23
 */
public class BaseController {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	protected static final TextResource textResource = new TextResource();

	protected static final MessageFormater messageFormater = new MessageFormater();

	protected static final Pattern SPACE_SPLIT_PATTERN = Pattern
			.compile("\\s+");

	protected static final String SYS_ERR_MSG = "系统异常，请稍后重试！";

	protected static final String NET_ERR_MSG = "网络超时，请稍后重试！";

	/**
	 * 是否以区块的形式显示页面
	 */
	protected boolean blockModel;

	@Autowired
	protected ViewLogicHelper viewLogicHelper;

	@Autowired
	protected HttpServletRequest request;

	private PermissionUtils permissionUtils;

	@ModelAttribute("textResource")
	public static TextResource getTextResource() {
		return textResource;
	}

	@ModelAttribute("messageFormater")
	public static MessageFormater getMessageFormater() {
		return messageFormater;
	}

	@ModelAttribute("viewLogicHelper")
	public ViewLogicHelper getViewLogicHelper() {
		return viewLogicHelper;
	}

	@ModelAttribute("permissionUtils")
	public PermissionUtils getPermissionUtils() {
		if (permissionUtils == null) {
			permissionUtils = new PermissionUtils(request);
		}
		return permissionUtils;
	}

	/**
	 * 通用错误处理
	 *
	 * @param e
	 */
	protected String handleException(Throwable e) {
		String errMsg = null;
		LOGGER.error("", e);
		if (e instanceof IllegalArgumentException) {
			errMsg = e.getMessage();
		} else if (e instanceof YeepayRuntimeException) {
			errMsg = e.getMessage();
			if (StringUtils.contains(errMsg, "DB2 SQL Error: ")) {
				errMsg = "数据库异常："
						+ StringUtils.substringBetween(errMsg,
						"DB2 SQL Error: ", "\n");
			}
		} else if (e instanceof YeepayBizException) {
			YeepayBizException be = (YeepayBizException) e;
			// 从数据字典取错误信息
			if (StringUtils.isNotBlank(be.getDefineCode())) {
				try {
					errMsg = TextResource.getExceptionMessage(be
							.getDefineCode());
				} catch (Exception ex) {
					LOGGER.error("", ex);
				}
			}
			// 数据字典中不存在错误信息，则默认采用异常信息
			if (StringUtils.isBlank(errMsg)) {
				errMsg = be.getMessage();
			}
			// 无异常信息，采用错误码
			if (StringUtils.isBlank(errMsg)) {
				errMsg = be.getDefineCode();
			}
		} else if (e instanceof SocketTimeoutException) {
			errMsg = "请求子系统超时";
		} else {
			errMsg = e.getMessage();
		}

		if (StringUtils.isBlank(errMsg)) {
			errMsg = SYS_ERR_MSG;
		} else if (StringUtils.contains(errMsg, "SocketTimeoutException")) {
			errMsg = NET_ERR_MSG;
		} else {
			errMsg = errMsg.split("\\n")[0];
		}
		return errMsg;
	}

	@ModelAttribute("blockModel")
	public void setBlockModel(boolean blockModel) {
		this.blockModel = blockModel;
	}

}
