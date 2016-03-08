package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.service.AppInfoService;
import com.yeepay.g3.core.soa.center.service.ServiceInfoService;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.QueryTypeEnum;
import com.yeepay.g3.facade.soa.center.facade.ServiceQueryFacade;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 *
 * @author：menghao.chen
 * @since：2014年7月3日 下午3:51:47
 * @version:
 */
@Service
public class ServiceQueryFacadeImpl implements ServiceQueryFacade {
	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private ServiceInfoService serviceInfoService;

	@Override
	public PageList queryService(ServiceQueryParam queryParam) {
		if (queryParam.getQueryType() == QueryTypeEnum.APP) {
			return appInfoService.queryApp(queryParam);
		} else {
			PageList serviceList = serviceInfoService.queryService(queryParam);
			if (serviceList.size() > 0) {
				List<AppInfoDTO> appList = appInfoService.queryAll();
				Map<String, AppInfoDTO> appMap = new HashMap<String, AppInfoDTO>();
				for (AppInfoDTO app : appList) {
					appMap.put(app.getAppName(), app);
				}
				for (Object o : serviceList) {
					ServiceInfoDTO service = (ServiceInfoDTO) o;
					service.setAppInfo(appMap.get(service.getAppName()));
				}
			}
			return serviceList;
		}
	}

	@Override
	public ServiceInfoDTO findService(String serviceInterface) {
		if (StringUtils.isBlank(serviceInterface)) {
			return null;
		}
		ServiceInfoDTO serviceInfo = serviceInfoService
				.findService(serviceInterface);
		if (serviceInfo != null) {
			serviceInfo.setAppInfo(appInfoService.findApp(serviceInfo
					.getAppName()));
		}
		return serviceInfo;
	}
}
