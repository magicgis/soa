/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.service.DeployRecoverService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.DeployRecordDTO;
import com.yeepay.g3.facade.soa.center.facade.DeployRecoverFacade;
import com.yeepay.g3.utils.event.ext.BaseEventUtils;

/**
 * @author：wang.bao
 * @since：2015年12月4日 下午1:39:26
 * @version:
 */
@Service
public class DeployRecoverFacadeImpl implements DeployRecoverFacade {
	/**
	 * 默认延迟5分钟，即5分钟后服务必须检查服务是否自动恢复
	 */
	private static Long DELAY = 5 * 60 * 1000L;

	@Autowired
	private DeployRecoverService deployRecoverService;

	@Override
	public Long deploy(String address, String operator, String reqAddress) {
		DeployRecordDTO record = new DeployRecordDTO();
		if (address.contains(":")) {
			address = StringUtils.substringBefore(address, ":");
		}
		record.setAddress(address);
		record.setOperator(operator);
		record.setReqAddress(reqAddress);
		record.setDeployTime(new Date());
		Long id = deployRecoverService.deploy(record);
		BaseEventUtils.sendEventWithDelay(SoaCenterConst.DEPLOY_RECOVER, DELAY,
				id);
		return id;
	}

	@Override
	public void recoverAll() {
		List<Long> ids = deployRecoverService.findAllUnRecovered();
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				BaseEventUtils.sendEvent(SoaCenterConst.DEPLOY_RECOVER, id);
			}
		}
	}
}
