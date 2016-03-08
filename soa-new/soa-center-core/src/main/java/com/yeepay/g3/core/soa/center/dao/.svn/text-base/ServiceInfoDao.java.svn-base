package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.ServiceInfo;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * @author：wang.bao
 * @since：2014年7月29日 上午10:23:04
 * @version:
 */
public interface ServiceInfoDao extends GenericDao<ServiceInfo> {

	ServiceInfo find(String serviceInterface);

	void batchChangeStatus(SoaStatusEnum fromStatus, SoaStatusEnum toStatus);

	PageList queryServiceInfo(ServiceQueryParam queryParam);

	void changeStatusByApp(String appName, SoaStatusEnum status);

	void deleteByApp(String appName);

	void changeStatus(SoaStatusEnum status, Long id);

	List<ServiceInfo> queryByApp(String appName);
}
