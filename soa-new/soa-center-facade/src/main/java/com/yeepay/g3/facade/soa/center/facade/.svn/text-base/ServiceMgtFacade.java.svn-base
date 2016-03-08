package com.yeepay.g3.facade.soa.center.facade;

import java.util.List;

import com.yeepay.g3.facade.soa.center.dto.MethodInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;

/**
 * 服务管理接口
 *
 * @author：menghao.chen
 * @since：2014年7月3日 下午2:34:49
 * @version:
 */
public interface ServiceMgtFacade {
	/**
	 * 根据id启用服务
	 *
	 * @param id
	 */
	void activeService(Long id);

	/**
	 * 根据id禁用服务
	 *
	 * @param id
	 */
	void forbidService(Long id);

	/**
	 * 根据id下线服务
	 *
	 * @param id
	 */
	void offlineService(Long id);

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
	 * 查询全部服务
	 */
	List<ServiceInfoDTO> queryAll(boolean isMonitor);

	/**
	 * 根据应用Id查询服务
	 */
	List<ServiceInfoDTO> queryByApp(String appName);

	/**
	 * 更新方法信息
	 *
	 * @param methodInfoDTO
	 */
	void updateMethod(MethodInfoDTO methodInfoDTO);

	/**
	 * 标记方法已下线
	 *
	 * @param id
	 */
	void offlineMethod(Long id);

	/**
	 * 删除方法
	 *
	 * @param id
	 */
	void deleteMethod(Long id);
}
