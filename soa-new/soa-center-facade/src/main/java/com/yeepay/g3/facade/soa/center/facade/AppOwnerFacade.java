package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;

import java.util.List;

/**
 * @author by menghao.chen
 */
public interface AppOwnerFacade {
	/**
	 * 根据应用名查询负责人
	 *
	 * @param appName
	 * @return
	 */
	List<AppOwnerDTO> queryByAppName(String appName);

	/**
	 * 根据依赖的应用名查询负责人
	 *
	 * @param depAppName
	 * @return
	 */
	List<AppOwnerDTO> queryByDepAppName(String depAppName);

	/**
	 * 根据登陆名查询负责人
	 *
	 * @param loginName
	 * @return
	 */
	List<AppOwnerDTO> queryByLoginName(String loginName);

	/**
	 * 批量更新关联
	 *
	 * @param loginName
	 * @param userName
	 * @param ownAppNameList
	 */
	void batchUpdate(String loginName, String userName, List<String> ownAppNameList);
}
