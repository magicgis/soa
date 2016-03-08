/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao;

import com.yeepay.g3.core.soa.center.entity.AppUpgradeInfo;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public interface AppUpgradeInfoDao extends GenericDao<AppUpgradeInfo> {
	void deleteAllByApp(String appName);

	AppUpgradeInfo findLatestUpgrade(String appName);
}
