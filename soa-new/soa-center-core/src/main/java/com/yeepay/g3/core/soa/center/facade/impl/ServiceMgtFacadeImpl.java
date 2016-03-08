package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yeepay.g3.core.soa.center.biz.RegistrySyncBiz;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;

import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.service.ServiceInfoService;
import com.yeepay.g3.facade.soa.center.dto.MethodInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.ServiceMgtFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.StringUtils;

/**
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 下午3:50:41
 * @version:
 */
@Service
public class ServiceMgtFacadeImpl implements ServiceMgtFacade {

	@Autowired
	private ServiceInfoService serviceInfoService;

	@Autowired
	private ProviderFacade providerFacade;

	@Autowired
	private ConsumerFacade consumerFacade;

	@Autowired
	private RegistrySyncBiz registrySyncBiz;

	@Override
	public void activeService(Long id) {
		CheckUtils.notEmpty(id, "id");
		serviceInfoService.changeStatus(SoaStatusEnum.ACTIVE, id);
	}

	@Override
	public void forbidService(Long id) {
		CheckUtils.notEmpty(id, "id");
		serviceInfoService.changeStatus(SoaStatusEnum.FORBID, id);
	}

	@Override
	public void offlineService(Long id) {
		CheckUtils.notEmpty(id, "id");
		serviceInfoService.changeStatus(SoaStatusEnum.OFFLINE, id);
	}

	@Override
	public void deleteService(Long id) {
		CheckUtils.notEmpty(id, "id");
		serviceInfoService.deleteService(id);
	}

	@Override
	public void updateService(ServiceInfoDTO serviceInfoDTO) {
		CheckUtils.notEmpty(serviceInfoDTO, "serviceInfoDTO");
		CheckUtils.notEmpty(serviceInfoDTO.getId(), "id");
		CheckUtils.notEmpty(serviceInfoDTO.getServiceName(), "serviceName");
		CheckUtils.notEmpty(serviceInfoDTO.getServiceDesc(), "serviceDesc");
		serviceInfoService.updateService(serviceInfoDTO);
	}

	@Override
	public List<ServiceInfoDTO> queryAll(boolean isMonitor) {
		if (!isMonitor) {
			throw new RuntimeException("抱歉，没有实现");
		} else {
			Set<String> services = registrySyncBiz.findAllServices();
			List<ServiceInfoDTO> serviceList = Lists.newArrayList();
			for (String serviceName : services) {
				ServiceInfoDTO serviceInfoDTO = new ServiceInfoDTO();
				serviceInfoDTO.setServiceName(serviceName);

				List<Provider> providers = providerFacade.findByService(serviceName);
				if (providers.size() > 0) {
					serviceInfoDTO.setAppName(providers.get(0).getApplication());
				}
				serviceInfoDTO.setProviderCount(providers.size());

				List<Consumer> consumers = consumerFacade.findByService(serviceName);
				if (consumers.size() > 0) {
					serviceInfoDTO.setAppName(consumers.get(0).getApplication());
				}
				serviceInfoDTO.setConsumerCount(consumers.size());
				serviceList.add(serviceInfoDTO);
			}
			return serviceList;
		}
	}

	@Override
	public List<ServiceInfoDTO> queryByApp(String appName) {
		if (StringUtils.isBlank(appName)) {
			return new ArrayList<ServiceInfoDTO>(0);
		}
		return serviceInfoService.queryByApp(appName);
	}

	@Override
	public void updateMethod(MethodInfoDTO methodInfoDTO) {
		CheckUtils.notEmpty(methodInfoDTO, "methodInfoDTO");
		CheckUtils.notEmpty(methodInfoDTO.getId(), "id");
		CheckUtils.notEmpty(methodInfoDTO.getMethodDesc(), "methodDesc");
		serviceInfoService.updateMethod(methodInfoDTO);
	}

	@Override
	public void offlineMethod(Long id) {
		CheckUtils.notEmpty(id, "id");
		serviceInfoService.changeMethodStatus(SoaStatusEnum.OFFLINE, id);
	}

	@Override
	public void deleteMethod(Long id) {
		CheckUtils.notEmpty(id, "id");
		serviceInfoService.deleteMethod(id);
	}
}
