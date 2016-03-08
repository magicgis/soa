/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao.impl;

import com.yeepay.g3.core.soa.center.dao.AppOwnerDao;
import com.yeepay.g3.core.soa.center.entity.AppOwner;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class AppOwnerDaoImpl extends GenericDaoDefault<AppOwner> implements
		AppOwnerDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AppOwner> queryByApp(String appName) {
		return this.query("queryByApp", appName);
	}

	@Override
	public void deleteAllByApp(String appName) {
		this.delete("deleteAllByApp", appName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppOwner> queryByDepAppName(String depAppName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("depAppName", depAppName);
		return this.query("queryByDepAppName", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppOwner> queryByLoginName(String loginName) {
		return this.query("queryByOwner", loginName);
	}

	@Override
	public void deleteAllByLoginName(String loginName) {
		this.delete("deleteAllByLoginName", loginName);
	}

	@Override
	public void batchAdd(List<AppOwner> appOwners) {
		this.batchInsert("batchInsert", appOwners);
	}
}
