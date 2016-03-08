/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.AppUpgradeInfoDao;
import com.yeepay.g3.core.soa.center.entity.AppUpgradeInfo;
import com.yeepay.g3.core.soa.center.service.AppUpgradeInfoService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class AppUpgradeInfoServiceImpl implements AppUpgradeInfoService {
	@Autowired
	private AppUpgradeInfoDao appUpgradeInfoDao;

	@Override
	public void addUpgradeInfo(AppUpgradeInfoDTO upgradeInfo) {
		appUpgradeInfoDao.add(DataConvertUtils.convert(upgradeInfo,
				AppUpgradeInfo.class));
	}

	@Override
	public void updateUpgradeInfo(AppUpgradeInfoDTO upgradeInfo) {
		this.appUpgradeInfoDao.update(DataConvertUtils.convert(upgradeInfo,
				AppUpgradeInfo.class));
	}

	@Override
	public void delUpgradeInfo(Long id) {
		appUpgradeInfoDao.delete(id);
	}
}
