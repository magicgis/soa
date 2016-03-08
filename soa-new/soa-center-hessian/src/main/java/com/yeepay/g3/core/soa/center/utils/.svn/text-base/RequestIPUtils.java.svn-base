/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.utils;

import javax.servlet.http.HttpServletRequest;

import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.ValidateUtils;

/**
 * @author：wang.bao
 * @since：2015年1月5日 下午2:27:55
 * @version:
 */
public class RequestIPUtils {

	public static String getRemoteAddr(HttpServletRequest request) {
		// 取得请求方IP地址
		// 处理设置了反向代理的情况
		String reqIp = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(reqIp) || "unknown".equalsIgnoreCase(reqIp)) {
			reqIp = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(reqIp) || "unknown".equalsIgnoreCase(reqIp)) {
			reqIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(reqIp) || "unknown".equalsIgnoreCase(reqIp)) {
			reqIp = request.getRemoteAddr();
		}

		// 果通过了多级反向代理的话，X-Forwarded-For的值并不止一个,取X-Forwarded-For中第一个非unknown的有效IP字符串
		if (StringUtils.isNotBlank(reqIp) && !ValidateUtils.isIP(reqIp)) {
			String[] allIps = StringUtils.split(reqIp);
			for (String ip : allIps) {
				if (ValidateUtils.isIP(ip)) {
					reqIp = ip;
					break;
				}
			}
		}
		return reqIp;
	}
}
