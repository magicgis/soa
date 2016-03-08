package com.yeepay.g3.core.soa.center.event;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.service.NetworkService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.event.ext.BaseEventListener;

/**
 *
 * @author：wang.bao
 * @since：2014年11月26日 下午6:14:36
 * @version:
 */
@Component
public class SyncNetworkHandler extends BaseEventListener {
	private final static Logger logger = LoggerFactory
			.getLogger(SyncNetworkHandler.class);

	@Autowired
	private NetworkService networkService;

	@SuppressWarnings("unchecked")
	@Override
	public void doAction(Object... args) {
		if (args == null || args.length < 2) {
			logger.info("参数不足");
			return;
		}
		String consumer = (String) args[0];
		Map<String, Boolean> providers = (Map<String, Boolean>) args[1];
		networkService.syncNetwork(consumer, providers);
	}

	@Override
	public String getListenedEventName() {
		return SoaCenterConst.SYNC_NETWORK_STATUS;
	}
}
