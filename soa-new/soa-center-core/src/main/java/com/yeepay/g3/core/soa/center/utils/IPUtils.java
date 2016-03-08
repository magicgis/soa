package com.yeepay.g3.core.soa.center.utils;

import java.util.regex.Pattern;

/**
 * Title: <br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 12:09
 */
public final class IPUtils {

	public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3}$");
	public static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
	public static final Pattern ALL_IP_PATTERN = Pattern.compile("0{1,3}(\\.0{1,3}){3}$");

}
