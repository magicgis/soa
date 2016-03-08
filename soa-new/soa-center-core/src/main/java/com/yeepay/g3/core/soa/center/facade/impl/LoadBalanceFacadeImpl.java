package com.yeepay.g3.core.soa.center.facade.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.facade.soa.center.dubbo.domain.LoadBalance;
import com.yeepay.g3.facade.soa.center.facade.LoadBalanceFacade;
import com.yeepay.g3.facade.soa.center.utils.OverrideUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;

/**
 * Title: 负载均衡 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 16:37
 */
@Service
public class LoadBalanceFacadeImpl implements LoadBalanceFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoadBalanceFacade.class);

	@Autowired
	private OverrideService overrideService;

	@Override
	public List<LoadBalance> findByService(String service) {
		return OverrideUtils.overridesToLoadBalances(overrideService.findByService(service));
	}

	@Override
	public List<LoadBalance> findAll() {
		return OverrideUtils.overridesToLoadBalances(overrideService.findAll());
	}

	@Override
	public LoadBalance findOne(Long id) {
		return OverrideUtils.overrideToLoadBalance(overrideService.findById(id));
	}

	@Override
	public void create(LoadBalance loadBalance, String operator) throws IOException {
		LOGGER.info("{} 创建负载均衡信息 loadBalance:{}", operator, loadBalance);
		loadBalance.setUsername(operator);
		overrideService.saveOverride(OverrideUtils.loadBalanceToOverride(loadBalance));
	}

	@Override
	public void edit(LoadBalance loadBalance, String operator) {
		LOGGER.info("{} 编辑负载均衡信息 {}", operator);
		overrideService.updateOverride(OverrideUtils.loadBalanceToOverride(loadBalance));
	}

	@Override
	public void delete(Long[] ids, String operator) {
		LOGGER.info("{} 删除负载均衡信息 {}", operator, ids);

		for (Long id : ids) {
			overrideService.deleteOverride(id);
		}
	}

}
