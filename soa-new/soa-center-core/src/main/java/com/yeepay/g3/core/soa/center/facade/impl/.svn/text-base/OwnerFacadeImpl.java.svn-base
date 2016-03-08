package com.yeepay.g3.core.soa.center.facade.impl;

import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.facade.soa.center.facade.OwnerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Title: 负责人 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:47
 */
@Component
public class OwnerFacadeImpl implements OwnerFacade {

	@Autowired
	private OverrideService overrideService;

	@java.lang.Override
	public void saveOverride(Override override) {
		overrideService.saveOverride(override);
	}

	@java.lang.Override
	public void updateOverride(Override override) {
		overrideService.updateOverride(override);
	}

	@java.lang.Override
	public void deleteOverride(Long id) {
		overrideService.deleteOverride(id);
	}

	@java.lang.Override
	public void enableOverride(Long id) {
		overrideService.enableOverride(id);
	}

	@java.lang.Override
	public void disableOverride(Long id) {
		overrideService.disableOverride(id);
	}

	@java.lang.Override
	public List<Override> findByService(String service) {
		return overrideService.findByService(service);
	}

	@java.lang.Override
	public List<Override> findByAddress(String address) {
		return overrideService.findByAddress(address);
	}

	@java.lang.Override
	public List<Override> findByServiceAndAddress(String service, String address) {
		return overrideService.findByServiceAndAddress(service, address);
	}

	@java.lang.Override
	public List<Override> findByApplication(String application) {
		return overrideService.findByApplication(application);
	}

	@java.lang.Override
	public List<Override> findByServiceAndApplication(String service, String application) {
		return overrideService.findByServiceAndApplication(service, application);
	}

	@java.lang.Override
	public List<Override> findAll() {
		return overrideService.findAll();
	}

	@java.lang.Override
	public Override findById(Long id) {
		return overrideService.findById(id);
	}

}
