package com.yeepay.g3.core.soa.center.facade.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.core.soa.center.utils.IPUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Weight;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.facade.soa.center.facade.WeightFacade;
import com.yeepay.g3.facade.soa.center.utils.OverrideUtils;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;

/**
 * Title: 权重调节 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 10:49
 */
@Service
public class WeightFacadeImpl implements WeightFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(WeightFacade.class);

	@Autowired
	private OverrideService overrideService;

	@Override
	public List<Weight> findByService(String service) {
		return OverrideUtils.overridesToWeights(overrideService.findByService(service));
	}

	@Override
	public List<Weight> findByAddress(String address) {
		return OverrideUtils.overridesToWeights(overrideService.findByAddress(address));
	}

	@Override
	public List<Weight> findAll() {
		return OverrideUtils.overridesToWeights(overrideService.findAll());
	}

	@Override
	public Weight findOne(Long id) {
		return OverrideUtils.overrideToWeight(overrideService.findById(id));
	}

	@Override
	public void create(Weight weight, String operator) throws IOException {
		LOGGER.info("{} 创建权重控制信息 weight:{}", operator, weight);

		Set<String> addresses = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new StringReader(weight.getAddress()));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;

				String ip = s;
				String port = null;
				if (s.contains(":")) {
					ip = s.substring(0, s.indexOf(":"));
					port = s.substring(s.indexOf(":") + 1, s.length());
					if (port.trim().length() == 0) port = null;
				}
				if (!IPUtils.IP_PATTERN.matcher(ip).matches()) {
					throw SOAException.ILLEGAL_IP_EXCEPTION
							.newInstance("非法IP: {0}", s);
				}
				if (IPUtils.LOCAL_IP_PATTERN.matcher(ip).matches() || IPUtils.ALL_IP_PATTERN.matcher(ip).matches()) {
					throw SOAException.ILLEGAL_PORT_EXCEPTION
							.newInstance("非法IP或端口: {0}", s);
				}
				if (port != null) {
					if (!NumberUtils.isDigits(port)) {
						throw SOAException.ILLEGAL_IP_OR_PORT_EXCEPTION
								.newInstance("非法端口: {0}", s);
					}
				}
				addresses.add(s);
			}
		}

		Set<String> aimServices = new HashSet<String>();
		reader = new BufferedReader(new StringReader(weight.getService()));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;
				// TODO 权限控制
//				if (!super.currentUser.hasServicePrivilege(s)) {
//					throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", s);
//					return false;
//				}
				aimServices.add(s);
			}
		}

		for (String aimService : aimServices) {
			for (String a : addresses) {
				Weight wt = new Weight();
				wt.setUsername(operator);
				wt.setAddress(Tool.getIP(a));
				wt.setService(aimService);
				wt.setWeight(weight.getWeight());
				overrideService.saveOverride(OverrideUtils.weightToOverride(wt));
			}
		}
	}

	@Override
	public void edit(Weight weight, String operator) {
		LOGGER.info("{} 编辑权重控制信息 {}", operator);

		// TODO 权限控制
//		if (!super.currentUser.hasServicePrivilege(weight.getService())) {
//			throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", s);
//		}
		weight.setAddress(Tool.getIP(weight.getAddress()));
		overrideService.updateOverride(OverrideUtils.weightToOverride(weight));
	}

	@Override
	public void delete(Long[] ids, String operator) {
		LOGGER.info("{} 删除权重控制信息 {}", operator, ids);

		// TODO 权限控制
//		for (Long id : ids) {
//			Weight w = OverrideUtils.overrideToWeight(overrideFacade.findById(id));
//				if (!super.currentUser.hasServicePrivilege(w.getService())) {
//					throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", s);
//					return false;
//				}
//		}

		for (Long id : ids) {
			overrideService.deleteOverride(id);
		}
	}

}
