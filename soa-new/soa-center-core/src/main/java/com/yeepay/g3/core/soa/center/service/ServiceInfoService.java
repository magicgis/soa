package com.yeepay.g3.core.soa.center.service;

import java.util.List;

import com.yeepay.g3.facade.soa.center.dto.MethodInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author：wang.bao
 * @since：2014年7月29日 上午10:23:04
 * @version:
 */
public interface ServiceInfoService {

	void syncServiceInfo(ServiceInfoDTO service);

	void syncMethodInfo(MethodInfoDTO method);

	void changeStatusByApp(String appName, SoaStatusEnum status);

	void deleteByApp(String appName);

	ServiceInfoDTO findService(String serviceName);

	ServiceInfoDTO findService(Long serviceId);

	/**
	 * 修改服务状态
	 */
	void changeStatus(SoaStatusEnum status, Long id);

	/**
	 * 根据id删除服务
	 *
	 * @param id
	 */
	void deleteService(Long id);

	/**
	 * 更新服务
	 *
	 * @param serviceInfoDTO
	 */
	void updateService(ServiceInfoDTO serviceInfoDTO);

	/**
	 * 根据应用Id查询服务
	 */
	List<ServiceInfoDTO> queryByApp(String appName);

	void initForSync();

	PageList queryService(ServiceQueryParam queryParam);

	void updateMethod(MethodInfoDTO methodInfoDTO);

	void changeMethodStatus(SoaStatusEnum offline, Long id);

	void deleteMethod(Long id);
}
