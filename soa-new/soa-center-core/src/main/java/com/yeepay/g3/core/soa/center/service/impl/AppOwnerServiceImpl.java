/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.service.impl;

import com.yeepay.g3.core.soa.center.dao.AppOwnerDao;
import com.yeepay.g3.core.soa.center.entity.AppOwner;
import com.yeepay.g3.core.soa.center.service.AppOwnerService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class AppOwnerServiceImpl implements AppOwnerService {
	@Autowired
	private AppOwnerDao appOwnerDao;

	@Override
	public void addOwner(AppOwnerDTO owner) {
		appOwnerDao.add(DataConvertUtils.convert(owner, AppOwner.class));
	}

	@Override
	public void delOwner(Long id) {
		appOwnerDao.delete(id);
	}

	@Override
	public List<AppOwnerDTO> queryByDepAppName(String depAppName) {
		List<AppOwner> appOwnerList = appOwnerDao.queryByDepAppName(depAppName);
		return DataConvertUtils.convert(appOwnerList, AppOwnerDTO.class);
	}

	@Override
	public List<AppOwnerDTO> queryByAppName(String appName) {
		List<AppOwner> appOwnerList = appOwnerDao.queryByApp(appName);
		return DataConvertUtils.convert(appOwnerList, AppOwnerDTO.class);
	}

	@Override
	public List<AppOwnerDTO> queryByLoginName(String loginName) {
		List<AppOwner> appOwnerList = appOwnerDao.queryByLoginName(loginName);
		return DataConvertUtils.convert(appOwnerList, AppOwnerDTO.class);
	}

	@Override
	public void deleteAllByLoginName(String loginName) {
		appOwnerDao.deleteAllByLoginName(loginName);
	}

	@Override
	public void batchAdd(List<AppOwnerDTO> appOwnerDTOs) {
		appOwnerDao.batchAdd(DataConvertUtils.convert(appOwnerDTOs, AppOwner.class));
	}
}
