/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.service.NetworkService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.facade.NetworkFacade;
import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.event.ext.BaseEventUtils;
import com.yeepay.g3.utils.soa.service.NetworkMonitorService;

/**
 * @author：wang.bao
 * @since：2014年9月19日 下午4:20:55
 * @version:
 */
@Service
public class NetworkFacadeImpl implements NetworkFacade, NetworkMonitorService {
	@Autowired
	private NetworkService networkService;

	@Override
	public void collect(String consumer, Map<String, Boolean> providers) {
		CheckUtils.notEmpty(consumer, "customer's address");
		CheckUtils.notEmpty(providers, "providers's address and status");
		BaseEventUtils.sendEvent(SoaCenterConst.SYNC_NETWORK_STATUS, consumer,
				providers);
	}

	@Override
	public PageList queryNetwork(NetworkQueryParam param) {
		return networkService.queryNetwork(param);
	}

	@Override
	public void deleteNetworkInfo(Long id) {
		CheckUtils.notNull(id, "id");
		networkService.deleteNetworkInfo(id);
	}

	@Override
	public void syncUnknown() {
		networkService.syncUnknown();
	}

	@Override
	public void clearUnknown() {
		networkService.clearUnknown();
	}
}
