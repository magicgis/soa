/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.MethodInfo;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public interface MethodInfoDao extends GenericDao<MethodInfo> {

	MethodInfo find(Long serviceId, String methodName);

	void batchChangeStatus(SoaStatusEnum fromStatus, SoaStatusEnum toStatus);

	List<MethodInfo> queryByServiceId(Long serviceId);

	List<MethodInfo> queryByServiceName(String serviceName);

	void changeStatusByApp(String appName, SoaStatusEnum status);

	void deleteByApp(String appName);

	void changeStatus(Long id, SoaStatusEnum status);
}
