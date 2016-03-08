package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yeepay.g3.core.soa.center.dao.AppAddressDao;
import com.yeepay.g3.core.soa.center.entity.AppAddress;
import com.yeepay.g3.facade.soa.center.enums.AppRoleEnum;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

/**
 * 应用-机器关系
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:17:31
 * @version:
 */
public class AppAddressDaoImpl extends GenericDaoDefault<AppAddress> implements
		AppAddressDao {

	@Override
	public AppAddress find(String appName, String address) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("address", address);
		return (AppAddress) this.queryOne("find", params);
	}

	@Override
	public void deleteByAddress(String address) {
		this.delete("deleteByAddress", address);
	}

	@Override
	public void deleteByApp(String appName) {
		this.delete("deleteByAddress", appName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryApps(String address, AppRoleEnum role) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("address", address);
		if (role != null) {
			params.put("role", role.name());
		}
		return this.query("queryApps", params);
	}

	@Override
	public void offline(String address) {
		this.delete("deleteAllByAddress", address);
	}
}
