/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.service;

import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;


/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public interface AppUpgradeInfoService {

	void addUpgradeInfo(AppUpgradeInfoDTO upgradeInfo);

	void updateUpgradeInfo(AppUpgradeInfoDTO upgradeInfo);

	void delUpgradeInfo(Long id);
}
