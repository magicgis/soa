/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao.impl;

import com.yeepay.g3.core.soa.center.dao.AppUpgradeInfoDao;
import com.yeepay.g3.core.soa.center.entity.AppUpgradeInfo;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class AppUpgradeInfoDaoImpl extends GenericDaoDefault<AppUpgradeInfo>
		implements AppUpgradeInfoDao {

	@Override
	public void deleteAllByApp(String appName) {
		this.delete("deleteAllByApp", appName);
	}

	@Override
	public AppUpgradeInfo findLatestUpgrade(String appName) {
		return (AppUpgradeInfo) this.queryOne("findLatestByApp", appName);
	}
}
