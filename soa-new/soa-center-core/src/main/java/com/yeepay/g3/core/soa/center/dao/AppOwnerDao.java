/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao;

import com.yeepay.g3.core.soa.center.entity.AppOwner;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.utils.persistence.GenericDao;

import java.util.List;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public interface AppOwnerDao extends GenericDao<AppOwner> {

	List<AppOwner> queryByApp(String appName);

	void deleteAllByApp(String appName);

	List<AppOwner> queryByDepAppName(String depAppName);

	List<AppOwner> queryByLoginName(String loginName);

	void deleteAllByLoginName(String loginName);

	void batchAdd(List<AppOwner> appOwners);
}
