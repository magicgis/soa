package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.AppDependency;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * 应用依赖关系
 *
 * @author：wang.bao
 * @since：2014年7月28日 下午7:28:14
 * @version:
 */
public interface AppDependencyDao extends GenericDao<AppDependency> {
	AppDependency find(String appName, String depAppName);

	void deleteAllByApp(String appName);

	/**
	 * 查询本应用依赖的所有应用
	 *
	 * @param appName
	 * @return
	 */
	List<AppDependency> queryDependency(String appName);

	/**
	 * 查询依赖本应用的所有应用
	 *
	 * @param appName
	 * @return
	 */
	List<AppDependency> queryReference(String appName);
}
