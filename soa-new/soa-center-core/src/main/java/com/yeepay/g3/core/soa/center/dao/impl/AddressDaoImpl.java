package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yeepay.g3.core.soa.center.dao.AddressDao;
import com.yeepay.g3.core.soa.center.entity.Address;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

/**
 * 机器信息
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:17:31
 * @version:
 */
public class AddressDaoImpl extends GenericDaoDefault<Address> implements
		AddressDao {

	@Override
	public Address find(String address) {
		return (Address) this.queryOne("find", address);
	}

	@Override
	public void deleteAddress(String address) {
		this.delete("deleteAddress", address);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> queryByApp(String appName) {
		return this.query("queryByApp", appName);
	}

	@Override
	public void batchChangeStatus(SoaStatusEnum fromStatus,
			SoaStatusEnum toStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromStatus", fromStatus);
		params.put("toStatus", toStatus);
		this.update("batchChangeStatus", params);
	}

	@Override
	public void offline(String address) {
		this.delete("deleteAll", address);
	}
}
