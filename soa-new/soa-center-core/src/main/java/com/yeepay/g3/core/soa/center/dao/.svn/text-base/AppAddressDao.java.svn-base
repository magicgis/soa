package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.AppAddress;
import com.yeepay.g3.facade.soa.center.enums.AppRoleEnum;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * 应用-机器关系
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午5:30:39
 * @version:
 */
public interface AppAddressDao extends GenericDao<AppAddress> {
	AppAddress find(String appName, String address);

	void deleteByAddress(String address);

	void deleteByApp(String appName);

	List<String> queryApps(String address, AppRoleEnum role);

	void offline(String address);
}
