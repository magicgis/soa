/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.service;

import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;

import java.util.List;


/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public interface AppOwnerService {
	void addOwner(AppOwnerDTO owner);

	void delOwner(Long id);

	/**
	 * 根据依赖的应用名查询所有消费者的拥有者
	 *
	 * @param depAppName
	 * @return
	 */
	List<AppOwnerDTO> queryByDepAppName(String depAppName);

	/**
	 * 根据应用名查询应用拥有者
	 *
	 * @param appName
	 * @return
	 */
	List<AppOwnerDTO> queryByAppName(String appName);

	/**
	 * 根据登录名查询应用拥有者
	 *
	 * @param loginName
	 * @return
	 */
	List<AppOwnerDTO> queryByLoginName(String loginName);

	/**
	 * 根据登录名删除关联
	 *
	 * @param loginName
	 */
	void deleteAllByLoginName(String loginName);

	/**
	 * 批量关联
	 *
	 * @param appOwnerDTOs
	 */
	void batchAdd(List<AppOwnerDTO> appOwnerDTOs);
}
