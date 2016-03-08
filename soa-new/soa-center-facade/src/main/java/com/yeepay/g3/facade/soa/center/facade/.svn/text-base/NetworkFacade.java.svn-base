/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author：wang.bao
 * @since：2014年9月19日 下午6:54:39
 * @version:
 */
public interface NetworkFacade {
	public PageList queryNetwork(NetworkQueryParam param);

	public void deleteNetworkInfo(Long id);

	/**
	 * 定时任务，超过15分钟未刷新的网络状态数据置为未知
	 */
	public void syncUnknown();

	public void clearUnknown();
}
