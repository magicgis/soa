package com.yeepay.g3.core.soa.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.MethodInfoDao;
import com.yeepay.g3.core.soa.center.dao.ServiceInfoDao;
import com.yeepay.g3.core.soa.center.entity.MethodInfo;
import com.yeepay.g3.core.soa.center.entity.ServiceInfo;
import com.yeepay.g3.core.soa.center.service.ServiceInfoService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
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
public class ServiceInfoServiceImpl implements ServiceInfoService {
	@Autowired
	private ServiceInfoDao serviceInfoDao;

	@Autowired
	private MethodInfoDao methodInfoDao;

	@Override
	public void initForSync() {
		serviceInfoDao.batchChangeStatus(SoaStatusEnum.ACTIVE,
				SoaStatusEnum.UNSYNC);
		methodInfoDao.batchChangeStatus(SoaStatusEnum.ACTIVE,
				SoaStatusEnum.UNSYNC);
	}

	@Override
	public void syncServiceInfo(ServiceInfoDTO ser) {
		ServiceInfo exist = serviceInfoDao.find(ser
				.getServiceInterfaceLowcase());
		if (exist == null) {
			exist = DataConvertUtils.convert(ser, ServiceInfo.class);
			serviceInfoDao.add(exist);
		} else {
			exist.setProviderCount(ser.getProviderCount());
			exist.setConsumerCount(ser.getConsumerCount());
			exist.setServiceProtocol(ser.getServiceProtocol());
			exist.setServiceSign(ser.getServiceSign());
			if (exist.getStatus() != SoaStatusEnum.FORBID) {
				exist.setStatus(SoaStatusEnum.ACTIVE);
			}
			serviceInfoDao.update(exist);
		}
		ser.setId(exist.getId());
	}

	@Override
	public void syncMethodInfo(MethodInfoDTO method) {
		MethodInfo exist = methodInfoDao.find(method.getServiceId(),
				method.getMethodName());
		if (exist == null) {
			methodInfoDao.add(DataConvertUtils
					.convert(method, MethodInfo.class));
		} else {
			exist.setServiceId(method.getServiceId());
			exist.setMethodName(method.getMethodName());
			if (exist.getStatus() != SoaStatusEnum.FORBID) {
				exist.setStatus(SoaStatusEnum.ACTIVE);
			}
			methodInfoDao.update(exist);
		}
	}

	@Override
	public void changeStatusByApp(String appName, SoaStatusEnum status) {
		serviceInfoDao.changeStatusByApp(appName, status);
		methodInfoDao.changeStatusByApp(appName, status);
	}

	@Override
	public void deleteByApp(String appName) {
		// 关联删除，需先删除方法信息
		methodInfoDao.deleteByApp(appName);
		serviceInfoDao.deleteByApp(appName);
	}

	@Override
	public ServiceInfoDTO findService(String serviceName) {
		ServiceInfoDTO serviceInfo = DataConvertUtils.convert(
				serviceInfoDao.find(serviceName.toLowerCase()),
				ServiceInfoDTO.class);
		this.appendDetailInfo(serviceInfo);
		return serviceInfo;
	}

	@Override
	public ServiceInfoDTO findService(Long serviceId) {
		ServiceInfoDTO serviceInfo = DataConvertUtils.convert(
				serviceInfoDao.get(serviceId), ServiceInfoDTO.class);
		return serviceInfo;
	}

	@Override
	public void changeStatus(SoaStatusEnum status, Long id) {
		this.serviceInfoDao.changeStatus(status, id);
	}

	@Override
	public void deleteService(Long id) {
		this.serviceInfoDao.delete(id);
	}

	@Override
	public void updateService(ServiceInfoDTO serviceInfoDTO) {
		ServiceInfo info = DataConvertUtils.convert(serviceInfoDTO,
				ServiceInfo.class);
		serviceInfoDao.update(info);
	}

	@Override
	public List<ServiceInfoDTO> queryByApp(String appName) {
		return DataConvertUtils.convert(serviceInfoDao.queryByApp(appName),
				ServiceInfoDTO.class);
	}

	@Override
	public PageList queryService(ServiceQueryParam queryParam) {
		return this.serviceInfoDao.queryServiceInfo(queryParam);
	}

	private void appendDetailInfo(ServiceInfoDTO serviceInfo) {
		if (serviceInfo == null) {
			return;
		}
		serviceInfo.setMethodList(DataConvertUtils.convert(
				this.methodInfoDao.queryByServiceId(serviceInfo.getId()),
				MethodInfoDTO.class));
	}

	@Override
	public void updateMethod(MethodInfoDTO methodInfoDTO) {
		methodInfoDao.update(DataConvertUtils.convert(methodInfoDTO,
				MethodInfo.class));
	}

	@Override
	public void changeMethodStatus(SoaStatusEnum status, Long id) {
		methodInfoDao.changeStatus(id, status);
	}

	@Override
	public void deleteMethod(Long id) {
		methodInfoDao.delete(id);
	}
}
