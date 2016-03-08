package com.yeepay.g3.facade.soa.center.facade;

import java.util.Map;

/**
 * IDC控制接口
 *
 * @author：wang.bao
 * @since：2015年7月28日 下午3:49:25
 * @version:
 */
public interface IDCControlFacade {
	/**
	 * 查询IDC相关信息
	 */
	Map<String, Object> queryInfo();

	/**
	 * 禁用IDC
	 */
	void disableIDC(String idcName);

	/**
	 * 启用IDC
	 */
	void enableIDC(String idcName);

	/**
	 * 调整跨机房流量比
	 */
	void changeCrossRate(int rate);

	/**
	 * 修改IDC-IP规则
	 */
	void changeIPRule(String idcName, String ipRule);
}
