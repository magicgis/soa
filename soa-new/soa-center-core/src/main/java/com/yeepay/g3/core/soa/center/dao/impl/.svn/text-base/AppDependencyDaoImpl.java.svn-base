package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yeepay.g3.core.soa.center.dao.AppDependencyDao;
import com.yeepay.g3.core.soa.center.entity.AppDependency;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

/**
 * 应用依赖关系
 *
 * @author：wang.bao
 * @since：2014年7月28日 下午7:28:14
 * @version:
 */
public class AppDependencyDaoImpl extends GenericDaoDefault<AppDependency>
		implements AppDependencyDao {

	@Override
	public AppDependency find(String appName, String depAppName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("depAppName", depAppName);
		return (AppDependency) this.queryOne("find", params);
	}

	@Override
	public void deleteAllByApp(String appName) {
		this.delete("deleteAllByApp", appName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppDependency> queryDependency(String appName) {
		return this.query("queryDependency", appName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppDependency> queryReference(String appName) {
		return this.query("queryReference", appName);
	}
}
