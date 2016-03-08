/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.monitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author：wang.bao
 * @since：2015年8月18日 上午9:07:53
 * @version:
 */
public class MonitorDataSaverFactory {
	private static Map<String, MonitorDataSaver> savers = new HashMap<String, MonitorDataSaver>();

	public static void register(MonitorDataSaver saver) {
		savers.put(saver.getName(), saver);
	}

	public static MonitorDataSaver getSaver(String name) {
		return savers.get(name);
	}
}
