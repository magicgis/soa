package com.yeepay.g3.core.soa.center.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.biz.RegistrySyncBiz;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.event.ext.BaseEventListener;

/**
 *
 * @author：wang.bao
 * @since：2014年8月13日 上午9:37:03
 * @version:
 */
@Component
public class SyncMethodInfoHandler extends BaseEventListener {
	private final static Logger logger = LoggerFactory
			.getLogger(SyncMethodInfoHandler.class);

	@Autowired
	private RegistrySyncBiz registrySyncBiz;

	@Override
	public void doAction(Object... args) {
		if (args == null || args.length < 1) {
			logger.info("参数不足");
			return;
		}
		String serviceName = (String) args[0];
		Long serviceId = null;
		if (args.length > 1) {
			serviceId = (Long) args[1];
		}
		registrySyncBiz.syncMethodInfo(serviceName, serviceId);
	}

	@Override
	public String getListenedEventName() {
		return SoaCenterConst.SYNC_METHOD_INFO;
	}
}
