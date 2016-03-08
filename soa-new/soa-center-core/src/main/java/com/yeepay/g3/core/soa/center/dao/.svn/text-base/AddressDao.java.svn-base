package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.Address;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * 应用机器信息
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午5:30:39
 * @version:
 */
public interface AddressDao extends GenericDao<Address> {
	Address find(String address);

	void deleteAddress(String address);

	/**
	 * 查询应用所在机器
	 *
	 * @param appName
	 * @return
	 */
	List<Address> queryByApp(String appName);

	void batchChangeStatus(SoaStatusEnum fromStatus, SoaStatusEnum toStatus);

	void offline(String address);
}
