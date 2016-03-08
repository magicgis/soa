/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.biz.RegistrySyncBiz;
import com.yeepay.g3.core.soa.center.dubbo.service.ConsumerService;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.RegistryServerSync;
import com.yeepay.g3.core.soa.center.service.AppInfoService;
import com.yeepay.g3.core.soa.center.service.ServiceInfoService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.facade.RegistrySyncFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.event.ext.BaseEventUtils;

/**
 * @author：wang.bao
 * @since：2014年8月8日 下午1:34:15
 * @version:
 */
@Service
public class RegistrySyncFacadeImpl implements RegistrySyncFacade {
	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private ServiceInfoService serviceInfoService;

	@Autowired
	private RegistrySyncBiz registrySyncBiz;

	@Autowired
	private RegistryServerSync registryServerSync;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ConsumerService consumerService;

	@Override
	@Transactional
	public void syncRegistry() {
		appInfoService.initForSync();
		serviceInfoService.initForSync();
		BaseEventUtils.sendEvent(SoaCenterConst.SYNC_APP_INFO);
		BaseEventUtils.sendEvent(SoaCenterConst.SYNC_ADDRESS_INFO);
	}

	@Override
	public void clearCache() {
		registrySyncBiz.clearCache();
	}

	@Override
	public void unregister(Long id) {
		CheckUtils.notEmpty(id, "id");
		Provider p = providerService.findProvider(id);
		if (p != null) {
			registryServerSync.unregister(p.toUrl());
		}
	}

	@Override
	public void unsubscribe(Long id) {
		CheckUtils.notEmpty(id, "id");
		Consumer c = consumerService.findConsumer(id);
		if (c != null) {
			registryServerSync.unsubscribe(c.toUrl());
		}
	}

}
