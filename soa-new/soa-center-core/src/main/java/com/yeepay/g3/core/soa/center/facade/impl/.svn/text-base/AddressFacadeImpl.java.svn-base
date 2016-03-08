/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.biz.AddressInfoBiz;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.service.AddressInfoService;
import com.yeepay.g3.core.soa.center.service.AppInfoService;
import com.yeepay.g3.core.soa.center.service.NetworkService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.AddressDTO;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.param.AddressQueryParam;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;
import com.yeepay.g3.utils.soa.consts.SOAConstants;

/**
 * @author：wang.bao
 * @since：2014年8月28日 上午10:23:17
 * @version:
 */
@Service
public class AddressFacadeImpl implements AddressFacade {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressFacadeImpl.class);

	private static final AntPathMatcher MATCHER = new AntPathMatcher();

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private AddressInfoBiz addressInfoBiz;

	@Autowired
	private AddressInfoService addressInfoService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private NetworkService networkService;

	@java.lang.Override
	public PageList queryAddress(AddressQueryParam queryParam) {
		List<AddressDTO> allAddresses = addressInfoBiz
				.findAllAddresses(queryParam.isWithCache());
		if (StringUtils.isNotBlank(queryParam.getServiceName())) {
			// 若已指定服务名，只查询服务提供者
			queryParam.setSide(Constants.PROVIDER_SIDE);
		}
		allAddresses = this.filterAddresses(allAddresses, queryParam);
		DataConvertUtils.sort(allAddresses, "address");
		PageList ret = DataConvertUtils.cutToPageList(allAddresses,
				queryParam.getPageNo(), queryParam.getPageSize());
		if (queryParam.isWithDetail()) {
			List<AppInfoDTO> appList = appInfoService.queryAll();
			Map<String, AppInfoDTO> appMap = new HashMap<String, AppInfoDTO>();
			for (AppInfoDTO app : appList) {
				appMap.put(app.getAppName(), app);
			}
			for (Object o : ret) {
				AddressDTO addr = (AddressDTO) o;
				List<AppInfoDTO> appDetailList = new ArrayList<AppInfoDTO>();
				for (String appName : addr.getAppList()) {
					appDetailList.add(appMap.get(appName));
				}
				addr.setAppDetailList(appDetailList);
			}
		}
		return ret;
	}

	private List<AddressDTO> filterAddresses(List<AddressDTO> allAddresses,
			AddressQueryParam queryParam) {
		List<AddressDTO> addresses = new ArrayList<AddressDTO>();
		for (AddressDTO address : allAddresses) {
			if (queryParam.getStatus() != null
					&& queryParam.getStatus() != address.getStatus()) {
				continue;
			}
			if (queryParam.getRole() != null
					&& queryParam.getRole() != address.getRole()) {
				continue;
			}
			if (!isMatch(queryParam.getSide(), address.getSide(), true)) {
				continue;
			}
			if (!isMatch(queryParam.getEnvironment(), address.getEnvironment(),
					true)) {
				continue;
			}
			if (!isMatch(queryParam.getAddress(), address.getAddress(), false)) {
				continue;
			}
			if (!isMatchOr(queryParam.getAppName(), address.getAppList())) {
				continue;
			}
			if (StringUtils.isNotBlank(queryParam.getServiceName())
					&& Constants.PROVIDER_SIDE.equals(address.getSide())
					&& !isMatchOr(queryParam.getServiceName(),
							providerService.findServicesByAddress(address
									.getAddress()))) {
				continue;
			}
			addresses.add(address);
		}
		return addresses;
	}

	private boolean isMatch(String queryInput, String value, boolean equals) {
		queryInput = StringUtils.trimToNull(queryInput);
		if (StringUtils.isBlank(queryInput)) {
			// 未输入此条件
			return true;
		}
		value = StringUtils.trimToNull(value);
		if (StringUtils.equals(queryInput, value)) {
			return true;
		}
		if (equals) {
			// 等值匹配但值不等
			return false;
		}
		// 模糊匹配
		return MATCHER.match(queryInput, value);
	}

	private boolean isMatchOr(String queryInput, Collection<String> values) {
		queryInput = StringUtils.trimToNull(queryInput);
		if (StringUtils.isBlank(queryInput)) {
			// 未输入此条件
			return true;
		}
		if (values == null || values.isEmpty()) {
			return false;
		}
		// 模糊匹配
		boolean matched = false;
		for (String value : values) {
			if (MATCHER.match(queryInput, value)) {
				matched = true;
				break;
			}
		}

		return matched;
	}

	@java.lang.Override
	public void changeInternal(String address, String appName) {
		if (!checkEnvIsTest(address)) {
			LOGGER.warn("非内测机不支持修改运行角色, address : " + address);
			return;
		}
		addressInfoBiz.changeAddressRole(address, AddressRoleEnum.IN);
	}

	@java.lang.Override
	public void changeExternal(String address, String appName) {
		if (!checkEnvIsTest(address)) {
			LOGGER.warn("非内测机不支持修改运行角色, address : " + address);
			return;
		}
		addressInfoBiz.changeAddressRole(address, AddressRoleEnum.OUT);
	}

	private boolean checkEnvIsTest(String address) {
		// 先查找此机器部署的应用
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setAddress(address);
		PageList list = this.queryAddress(queryParam);
		if (list == null || list.size() < 1) {
			LOGGER.warn("禁用机器：" + address + "，此机器未部署SOA服务提供者应用。");
		} else {
			return SOAConstants.ENV_TEST.equals(((AddressDTO) list.get(0))
					.getEnvironment());
		}
		return false;
	}

	@java.lang.Override
	public void disable(String address, boolean includeConsumer) {
		CheckUtils.notEmpty(address, "address");
		// 先查找此机器部署的应用
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setSide(Constants.PROVIDER);
		queryParam.setAddress(address);
		PageList list = this.queryAddress(queryParam);
		if (list == null || list.size() < 1) {
			LOGGER.warn("禁用机器：" + address + "，此机器未部署SOA服务提供者应用。");
		} else {
			Set<String> apps = ((AddressDTO) list.get(0)).getAppList();
			String appName = apps.iterator().next();
			LOGGER.warn("禁用机器：" + address + "，此机器部署如下SOA应用：" + apps);
			// 根据应用查找所有可用机器
			AddressQueryParam queryParam2 = new AddressQueryParam();
			queryParam2.setAppName(appName);
			queryParam2.setSide(Constants.PROVIDER);
			queryParam2.setEnvironment("product");
			queryParam2.setStatus(SoaStatusEnum.ACTIVE);
			PageList list2 = this.queryAddress(queryParam2);
			if (list2 != null && list2.size() == 1) {
				AddressDTO addr = (AddressDTO) list2.get(0);
				// addr.getAddress()带有端口号
				if (addr.getAddress().startsWith(address)) {
					LOGGER.warn("禁用机器：" + address + "，不能禁用所有机器，否则将没有服务提供者。");
					throw new RuntimeException("不能禁用所有机器，否则将没有服务提供者。");
				} else {
					LOGGER.warn("禁用机器：" + address + "，此机器未部署应用：" + appName
							+ "或非启用状态，请检查应用部署情况（" + apps + "）。");
				}
			}
		}
		// 非异常情况都要执行禁用操作，以支持一次禁用不完全，可继续禁用
		addressInfoBiz.changeAddressStatus(address, includeConsumer, false);
	}

	@java.lang.Override
	public void enable(String address, boolean includeConsumer) {
		CheckUtils.notEmpty(address, "address");
		addressInfoBiz.changeAddressStatus(address, includeConsumer, true);
	}

	@java.lang.Override
	public void enableAll() {
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setStatus(SoaStatusEnum.FORBID);
		PageList list = this.queryAddress(queryParam);
		if (list != null && list.getData() != null) {
			for (Object obj : list.getData()) {
				AddressDTO addr = (AddressDTO) obj;
				addressInfoBiz.changeAddressStatus(addr.getAddress(), true,
						true);
			}
		}
	}

	@Override
	@Transactional
	public void offline(String address) {
		address = Tool.getIP(address);
		CheckUtils.notEmpty(address, "address");
		addressInfoService.offline(address);
		networkService.offline(address);
	}

	@Override
	public Set<String> findNoProvoiderApps() {
		Set<String> apps = new HashSet<String>();
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setSide(Constants.PROVIDER);
		queryParam.setStatus(SoaStatusEnum.FORBID);
		PageList list = this.queryAddress(queryParam);
		if (list == null || list.size() < 1) {
			LOGGER.info("没有被禁用的机器");
			return apps;
		}

		Set<String> allApps = new HashSet<String>();
		for (Object obj : list) {
			AddressDTO addr = (AddressDTO) obj;
			allApps.addAll(addr.getAppList());
		}

		for (String app : allApps) {
			AddressQueryParam q = new AddressQueryParam();
			q.setAppName(app);
			q.setSide(Constants.PROVIDER);
			q.setEnvironment("product");
			q.setStatus(SoaStatusEnum.ACTIVE);
			PageList l = this.queryAddress(q);
			if (l == null || l.size() < 1) {
				apps.add(app);
			}
		}
		LOGGER.info("No Provider Apps : " + apps);
		return apps;
	}
}
