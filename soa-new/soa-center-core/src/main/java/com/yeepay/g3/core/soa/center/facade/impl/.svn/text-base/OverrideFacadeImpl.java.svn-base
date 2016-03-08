package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.facade.soa.center.facade.OverrideFacade;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * Title: 动态配置 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:44
 */
@Service
public class OverrideFacadeImpl implements OverrideFacade {

	protected static final Logger LOGGER = LoggerFactory.getLogger(OverrideFacadeImpl.class);

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
	public void deleteOverride(Long[] ids) {
		for (Long id : ids) {
			try {
				overrideService.deleteOverride(id);
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}
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
	public List<Override> findByServiceAndApplication(String service,
			String application) {
		return overrideService
				.findByServiceAndApplication(service, application);
	}

	@java.lang.Override
	public List<Override> findAll() {
		return overrideService.findAll();
	}

	@java.lang.Override
	public PageList findAll(int pageNo, int pageSize) {
		return DataConvertUtils.cutToPageList(this.findAll(), pageNo, pageSize);
	}

	@java.lang.Override
	public Override findById(Long id) {
		return overrideService.findById(id);
	}

	@java.lang.Override
	public void forbid(Long[] ids, String operator, String operatorAddress) {
		for (Long id : ids) {
			overrideService.disableOverride(id);
		}
	}

	@java.lang.Override
	public void allow(Long[] ids, String operator, String operatorAddress) {
		for (Long id : ids) {
			overrideService.enableOverride(id);
		}
	}

}
