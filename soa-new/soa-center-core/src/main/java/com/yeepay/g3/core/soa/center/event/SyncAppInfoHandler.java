package com.yeepay.g3.core.soa.center.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.biz.RegistrySyncBiz;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.utils.event.ext.BaseEventListener;

/**
 *
 * @author：wang.bao
 * @since：2014年8月13日 上午9:37:03
 * @version:
 */
@Component
public class SyncAppInfoHandler extends BaseEventListener {
	@Autowired
	private RegistrySyncBiz registrySyncBiz;

	@Override
	public void doAction(Object... arg0) {
		registrySyncBiz.clearCache();
		registrySyncBiz.syncAppInfo();
	}

	@Override
	public String getListenedEventName() {
		return SoaCenterConst.SYNC_APP_INFO;
	}
}
