/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.facade;

/**
 * 注册中心信息同步
 *
 * @author：wang.bao
 * @since：2014年8月8日 下午1:33:05
 * @version:
 */
public interface RegistrySyncFacade {
	/**
	 * 同步注册中心
	 */
	public void syncRegistry();

	/**
	 * 清除缓存
	 */
	public void clearCache();

	public void unregister(Long id);

	void unsubscribe(Long id);
}
