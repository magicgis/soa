/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.facade;

/**
 * 服务自动启用禁用控制
 *
 * @author：wang.bao
 * @since：2015年12月4日 下午1:09:23
 * @version:
 */
public interface DeployRecoverFacade {
	/**
	 * 上线发布，生成预约自动恢复记录，同时可用于追溯部署历史
	 */
	Long deploy(String address, String operator, String reqAddress);

	/**
	 * 恢复全部，用于定时任务
	 */
	void recoverAll();
}
