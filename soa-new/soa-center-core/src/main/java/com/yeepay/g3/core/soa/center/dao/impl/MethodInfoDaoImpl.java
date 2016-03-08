/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yeepay.g3.core.soa.center.dao.MethodInfoDao;
import com.yeepay.g3.core.soa.center.entity.MethodInfo;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class MethodInfoDaoImpl extends GenericDaoDefault<MethodInfo> implements
		MethodInfoDao {

	@Override
	public MethodInfo find(Long serviceId, String methodName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serviceId", serviceId);
		params.put("methodName", methodName);
		return (MethodInfo) this.queryOne("find", params);
	}

	@Override
	public void batchChangeStatus(SoaStatusEnum fromStatus,
			SoaStatusEnum toStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromStatus", fromStatus);
		params.put("toStatus", toStatus);
		this.update("batchChangeStatus", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MethodInfo> queryByServiceId(Long serviceId) {
		return this.query("queryByServiceId", serviceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MethodInfo> queryByServiceName(String serviceName) {
		return this.query("queryByServiceName", serviceName);
	}

	@Override
	public void changeStatusByApp(String appName, SoaStatusEnum status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("status", status);
		this.update("changeStatusByApp", params);
	}

	@Override
	public void deleteByApp(String appName) {
		this.update("deleteByApp", appName);
	}

	@Override
	public void changeStatus(Long id, SoaStatusEnum status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status);
		this.update("changeStatus", params);
	}
}
