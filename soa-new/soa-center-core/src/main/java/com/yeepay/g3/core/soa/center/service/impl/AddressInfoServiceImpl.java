package com.yeepay.g3.core.soa.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.AddressDao;
import com.yeepay.g3.core.soa.center.dao.AppAddressDao;
import com.yeepay.g3.core.soa.center.entity.Address;
import com.yeepay.g3.core.soa.center.entity.AppAddress;
import com.yeepay.g3.core.soa.center.service.AddressInfoService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.AddressDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;

/**
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午10:56:15
 * @version:
 */
public class AddressInfoServiceImpl implements AddressInfoService {
	@Autowired
	private AddressDao addressDao;

	@Autowired
	private AppAddressDao appAddressDao;

	@Override
	public void initForSync() {
		addressDao
				.batchChangeStatus(SoaStatusEnum.ACTIVE, SoaStatusEnum.UNSYNC);
	}

	@Override
	public void syncDownAddress() {
		addressDao.batchChangeStatus(SoaStatusEnum.UNSYNC, SoaStatusEnum.DOWN);
	}

	@Override
	public void syncAddress(List<AddressDTO> addressList) {
		for (AddressDTO address : addressList) {
			Address addr = addressDao.find(address.getAddress());
			if (addr == null) {
				addr = DataConvertUtils.convert(address, Address.class);
				addressDao.add(addr);
			} else {
				addr.setStatus(address.getStatus());
				addr.setEnvironment(address.getEnvironment());
				addr.setRole(address.getRole());
				addressDao.update(addr);
			}
			for (String appName : address.getAppList()) {
				AppAddress relation = appAddressDao.find(appName,
						address.getAddress());
				if (relation == null) {
					relation = new AppAddress();
					relation.setAppName(appName);
					relation.setAddress(address.getAddress());
					appAddressDao.add(relation);
				}
			}
		}
	}

	@Override
	public void offline(String address) {
		addressDao.offline(address);
		appAddressDao.offline(address);
	}
}
