/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.service;

import java.util.List;

import com.yeepay.g3.facade.soa.center.dto.DeployRecordDTO;

/**
 * 服务自动启用禁用控制
 *
 * @author：wang.bao
 * @since：2015年12月4日 下午1:09:23
 * @version:
 */
public interface DeployRecoverService {
	/**
	 * 上线发布，生成预约自动恢复记录，同时可用于追溯部署历史
	 */
	Long deploy(DeployRecordDTO record);

	DeployRecordDTO findById(Long id);

	/**
	 * 查找未恢复的部署记录
	 */
	List<Long> findAllUnRecovered();

	/**
	 * 恢复服务
	 */
	void recover(Long id);
}
