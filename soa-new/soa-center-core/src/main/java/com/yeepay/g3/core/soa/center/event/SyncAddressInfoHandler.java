package com.yeepay.g3.core.soa.center.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.biz.AddressInfoBiz;
import com.yeepay.g3.core.soa.center.service.AddressInfoService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.AddressDTO;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.event.ext.BaseEventListener;

/**
 * 同步机器-应用关联信息
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午11:14:13
 * @version:
 */
@Component
public class SyncAddressInfoHandler extends BaseEventListener {
	private final static Logger logger = LoggerFactory
			.getLogger(SyncAddressInfoHandler.class);

	@Autowired
	private AddressInfoBiz addressInfoBiz;

	@Autowired
	private AddressInfoService addressInfoService;

	@Override
	public void doAction(Object... args) {
		try {
			addressInfoService.initForSync();
			List<AddressDTO> addressList = addressInfoBiz.findAllAddresses(false);
			addressInfoService.syncAddress(addressList);
			addressInfoService.syncDownAddress();
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	@Override
	public String getListenedEventName() {
		return SoaCenterConst.SYNC_ADDRESS_INFO;
	}
}
