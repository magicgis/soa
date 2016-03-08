package com.yeepay.g3.core.soa.center.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.service.AppOwnerService;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.facade.AppOwnerFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by menghao.chen
 */
@Service
public class AppOwnerFacadeImpl implements AppOwnerFacade {
	@Autowired
	private AppOwnerService appOwnerService;

	@Override
	public List<AppOwnerDTO> queryByAppName(String appName) {
		if (StringUtils.isBlank(appName)) {
			return null;
		}
		return appOwnerService.queryByAppName(appName);
	}

	@Override
	public List<AppOwnerDTO> queryByDepAppName(String depAppName) {
		if (StringUtils.isBlank(depAppName)) {
			return null;
		}
		return appOwnerService.queryByDepAppName(depAppName);
	}

	@Override
	public List<AppOwnerDTO> queryByLoginName(String loginName) {
		if (StringUtils.isBlank(loginName)) {
			return null;
		}
		return appOwnerService.queryByLoginName(loginName);
	}

	@Transactional
	@Override
	public void batchUpdate(String loginName, String userName, List<String> ownAppNameList) {
		CheckUtils.notEmpty(loginName, "loginName");
		CheckUtils.notEmpty(userName, "userName");
		appOwnerService.deleteAllByLoginName(loginName);
		if (CollectionUtils.isNotEmpty(ownAppNameList)) {
			List<AppOwnerDTO> appOwnerDTOs = new ArrayList<AppOwnerDTO>();
			for (String appName : ownAppNameList) {
				appOwnerDTOs.add(new AppOwnerDTO(appName, loginName, userName));
			}
			appOwnerService.batchAdd(appOwnerDTOs);
		}
	}
}
