/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.utils;

import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.config.ConfigParam;
import com.yeepay.g3.utils.config.ConfigurationUtils;

/**
 * SOA相关统一配置
 *
 * @author：wang.bao
 * @since：2014年8月11日 下午4:16:26
 * @version:
 */
public class SoaConfigUtils {
	/**
	 * javadoc根目录
	 */
	private static final String SOA_JAVADOC_CTXPATH = "soa.javadoc.ctxpath";

	/**
	 * javadoc根目录
	 */
	@SuppressWarnings("unchecked")
	public static String getJavadocCtxPath(String defaultValue) {
		String retValue = defaultValue;
		try {
			ConfigParam<String> config = ConfigurationUtils
					.getSysConfigParam(SOA_JAVADOC_CTXPATH);
			if (config != null && StringUtils.isNotBlank(config.getValue())) {
				retValue = config.getValue();
			}
		} catch (Exception e) {
		}
		if (!StringUtils.endsWith(retValue, "/")) {
			retValue = StringUtils.defaultIfNull(retValue) + "/";
		}
		return retValue;
	}

}
