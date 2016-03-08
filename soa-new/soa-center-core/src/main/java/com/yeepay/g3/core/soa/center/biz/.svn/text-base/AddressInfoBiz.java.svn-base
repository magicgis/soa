/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.biz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.google.common.collect.Sets;
import com.yeepay.g3.core.soa.center.dubbo.service.ConsumerService;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.RegistryServerSync;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.AddressDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.ConfigFacade;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.cache.remote.RemoteCacheUtils;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;
import com.yeepay.g3.utils.soa.config.SoaConfigurationUtils;
import com.yeepay.g3.utils.soa.config.model.Config;
import com.yeepay.g3.utils.soa.config.model.ConfigDataTypeEnum;
import com.yeepay.g3.utils.soa.consts.SOAConstants;
import com.yeepay.g3.utils.soa.context.ApplicationContextHelper;
import com.yeepay.g3.utils.soa.rpc.cluster.router.env.EnvConditionRouterHelper;

/**
 * @author：wang.bao
 * @since：2014年8月28日 上午10:23:17
 * @version:
 */
@Component
public class AddressInfoBiz {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AddressInfoBiz.class);

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ProviderFacade providerFacade;

	@Autowired
	private ConsumerFacade consumerFacade;

	@Autowired
	private ConfigFacade configFacade;

	@Autowired
	private RegistryServerSync sync;

	private List<AddressDTO> findAllAddresses(Set<String> addresses, String side) {
		List<AddressDTO> allAddresses = new ArrayList<AddressDTO>();
		if (StringUtils.equals(side, Constants.PROVIDER)) {
			for (String address : addresses) {
				Set<String> appList = Sets.newHashSet();
				List<Provider> providers = this.providerService
						.findByAddress(address);
				AddressDTO addr = new AddressDTO();
				addr.setAddress(address);
				addr.setAppList(appList);
				addr.setSide(Constants.PROVIDER);
				for (Provider p : providers) {
					if (StringUtils.isBlank(addr.getEnvironment())) {
						addr.setEnvironment(EnvConditionRouterHelper
								.getEnvironment(p.toUrl()));
						addr.setRole(EnvConditionRouterHelper.getEnvRole(p
								.toUrl()));
						addr.setStatus(this.getRealStatus(p));
					}
					if (StringUtils.isNotBlank(p.getApplication())) {
						appList.add(p.getApplication());
					}
				}
				allAddresses.add(addr);
			}
		} else {
			Set<String> skipApps = Sets.newHashSet();
			if (StringUtils.equals(side, Constants.CONSUMER)) {
				// 当查询消费者时，构造应用列表时跳过提供者
				skipApps.addAll(providerService.findApplications());
			}
			for (String address : addresses) {
				Set<String> appList = Sets.newHashSet();
				List<Consumer> consumers = this.consumerService
						.findByAddress(address);
				AddressDTO addr = new AddressDTO();
				addr.setAddress(address);
				addr.setAppList(appList);
				addr.setSide(Constants.CONSUMER);
				for (Consumer c : consumers) {
					if (StringUtils.isBlank(addr.getEnvironment())) {
						addr.setEnvironment(EnvConditionRouterHelper
								.getEnvironment(c.toUrl()));
						addr.setRole(EnvConditionRouterHelper.getEnvRole(c
								.toUrl()));
						addr.setStatus(this.getRealStatus(c));
					}
					if (StringUtils.isNotBlank(c.getApplication())
							&& !skipApps.contains(c.getApplication())) {
						appList.add(c.getApplication());
					}
				}
				if (!appList.isEmpty()) {
					allAddresses.add(addr);
				}
			}
		}
		return allAddresses;
	}

	@SuppressWarnings("unchecked")
	public List<AddressDTO> findAllAddresses(boolean withCache) {
		List<AddressDTO> ret = null;
		if (withCache) {
			ret = (List<AddressDTO>) RemoteCacheUtils
					.get(SoaCenterConst.CACHE_ADDR_INFO);
			if (ret != null && !ret.isEmpty()) {
				return ret;
			}
		}
		Map<String, Set<String>> addrAppMapping = new HashMap<String, Set<String>>();
		ret = new ArrayList<AddressDTO>();
		ConcurrentMap<String, Map<Long, URL>> providerUrls = sync
				.getRegistryCache().get(Constants.PROVIDERS_CATEGORY);
		this.findAddresses(ret, addrAppMapping, providerUrls,
				Constants.PROVIDER);
		ConcurrentMap<String, Map<Long, URL>> consumerUrls = sync
				.getRegistryCache().get(Constants.CONSUMERS_CATEGORY);
		this.findAddresses(ret, addrAppMapping, consumerUrls,
				Constants.CONSUMER);
		RemoteCacheUtils.put(SoaCenterConst.CACHE_ADDR_INFO, ret, 600);
		return ret;
	}

	private void findAddresses(List<AddressDTO> retAddressList,
			Map<String, Set<String>> addrAppMapping,
			ConcurrentMap<String, Map<Long, URL>> urls, String side) {
		if (null != urls) {
			Set<String> skipApps = Sets.newHashSet();
			if (StringUtils.equals(side, Constants.CONSUMER)) {
				// 当查询消费者时，构造应用列表时跳过提供者
				skipApps.addAll(providerService.findApplications());
			}
			for (Map.Entry<String, Map<Long, URL>> e1 : urls.entrySet()) {
				Map<Long, URL> value = e1.getValue();
				for (Map.Entry<Long, URL> e2 : value.entrySet()) {
					URL url = e2.getValue();
					String addr = url.getAddress();
					if (StringUtils.isBlank(addr)) {
						continue;
					}
					String application = url
							.getParameter(Constants.APPLICATION_KEY);
					if (!addrAppMapping.containsKey(addr)) {
						Set<String> apps = Sets.newHashSet();
						if (StringUtils.isNotBlank(application)
								&& !skipApps.contains(application)) {
							apps.add(application);
						}
						addrAppMapping.put(addr, apps);

						AddressDTO address = new AddressDTO();
						address.setAddress(addr);
						address.setSide(side);
						address.setEnvironment(EnvConditionRouterHelper
								.getEnvironment(url));
						address.setRole(EnvConditionRouterHelper
								.getEnvRole(url));
						address.setAppList(apps);
						if (StringUtils.equals(side, Constants.PROVIDER)) {
							List<Provider> providers = providerService
									.findByAddress(addr);
							if (providers != null && !providers.isEmpty()) {
								address.setStatus(this.getRealStatus(providers
										.get(0)));
							} else {
								address.setStatus(this.getRealStatus(url));
							}
						} else {
							List<Consumer> consumers = consumerService
									.findByAddress(addr);
							if (consumers != null && !consumers.isEmpty()) {
								address.setStatus(this.getRealStatus(consumers
										.get(0)));
							} else {
								address.setStatus(this.getRealStatus(url));
							}
						}
						retAddressList.add(address);
					} else if (StringUtils.isNotBlank(application)
							&& !skipApps.contains(application)) {
						Set<String> apps = addrAppMapping.get(addr);
						apps.add(application);
					}
				}
			}
			if (StringUtils.equals(side, Constants.CONSUMER)) {
				this.filterBlankConsumer(retAddressList);
			}
		}
	}

	private SoaStatusEnum getRealStatus(Provider provider) {
		Provider p = providerFacade.findProviderDetail(provider.getId());
		if (p.getOverride() != null) {
			return SoaStatusEnum.FORBID;
		}
		return SoaStatusEnum.ACTIVE;
	}

	private SoaStatusEnum getRealStatus(Consumer consumer) {
		return consumerFacade.isInBlackList(consumer) ? SoaStatusEnum.FORBID
				: SoaStatusEnum.ACTIVE;
	}

	private SoaStatusEnum getRealStatus(URL url) {
		return url.getParameter(Constants.ENABLED_KEY, true) ? SoaStatusEnum.ACTIVE
				: SoaStatusEnum.FORBID;
	}

	public List<AddressDTO> queryByApp(String appName, String side) {
		List<AddressDTO> allAddresses = null;
		Set<String> addresses = Sets.newHashSet();
		if (StringUtils.equals(side, Constants.PROVIDER)) {
			addresses.addAll(providerService
					.findAddressesByApplication(appName));
			allAddresses = this.findAllAddresses(addresses, Constants.PROVIDER);
		} else if (StringUtils.equals(side, Constants.CONSUMER)) {
			addresses.addAll(consumerService
					.findAddressesByApplication(appName));
			allAddresses = this.findAllAddresses(addresses, Constants.CONSUMER);
		} else {
			addresses.addAll(providerService
					.findAddressesByApplication(appName));
			allAddresses = this.findAllAddresses(addresses, Constants.PROVIDER);
			if (addresses.isEmpty()) {
				addresses.addAll(consumerService
						.findAddressesByApplication(appName));
				allAddresses.addAll(findAllAddresses(addresses,
						Constants.CONSUMER));
			}
		}
		return allAddresses;
	}

	public List<AddressDTO> queryByService(String serviceName, String side) {
		List<AddressDTO> allAddresses = null;
		Set<String> addresses = Sets.newHashSet();
		if (StringUtils.equals(side, Constants.PROVIDER)) {
			addresses.addAll(providerService
					.findAddressesByService(serviceName));
			allAddresses = this.findAllAddresses(addresses, Constants.PROVIDER);
		} else if (StringUtils.equals(side, Constants.CONSUMER)) {
			addresses.addAll(consumerService
					.findAddressesByService(serviceName));
			allAddresses = this.findAllAddresses(addresses, Constants.CONSUMER);
		} else {
			addresses.addAll(providerService
					.findAddressesByService(serviceName));
			allAddresses = this.findAllAddresses(addresses, Constants.PROVIDER);
			if (addresses.isEmpty()) {
				addresses.addAll(consumerService
						.findAddressesByService(serviceName));
				allAddresses.addAll(findAllAddresses(addresses,
						Constants.CONSUMER));
			}
		}
		allAddresses = this.findAllAddresses(addresses, side);
		return allAddresses;
	}

	public void changeAddressRole(String address, AddressRoleEnum toRole) {
		CheckUtils.notEmpty(address, "address");
		CheckUtils.notEmpty(toRole, "role");
		// 去端口号取IP
		address = Tool.getIP(address);
		List<Provider> providers = this.providerService.findByAddress(address);
		for (Provider p : providers) {
			try {
				URL url = p.toUrl();
				AddressRoleEnum role = EnvConditionRouterHelper.getEnvRole(url);
				if (role == toRole) {
					continue;
				}
				url = url.addParameter(SOAConstants.KEY_ROLE, toRole.name());
				p.setParameters(url.toParameterString());
				providerService.updateProvider(p);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		// 同步机器角色
		syncAddressRole(address, toRole);
		RemoteCacheUtils.remove(SoaCenterConst.CACHE_ADDR_INFO);
	}

	@SuppressWarnings("unchecked")
	private void syncAddressRole(String address, AddressRoleEnum toRole) {
		Config config = SoaConfigurationUtils
				.getConfigObject(SOAConstants.ROUTER_ENV_OUT);
		if (toRole == AddressRoleEnum.OUT) {
			// 内测机对外
			if (config == null) {
				// 首次、添加配置项
				config = new Config(SOAConstants.CONFIG_COMMONCFG, "内测机忙时对外列表",
						SOAConstants.ROUTER_ENV_OUT, Arrays.asList(address),
						ConfigDataTypeEnum.STRING);
				config.setEnabled(true);
				configFacade.addConfig(config);
			} else {
				// 已有配置，添加IP
				List<String> list = (List<String>) config.getValue();
				if (!list.contains(address)) {
					list.add(address);
					configFacade.updateConfig(config);
				}
			}
		} else {
			// 内测机对内
			if (config != null) {
				// 有配置则删除IP
				List<String> list = (List<String>) config.getValue();
				list.remove(address);
				if (list.isEmpty()) {
					configFacade.deleteConfig(config.getKey(),
							config.getDomain());
				} else {
					configFacade.updateConfig(config);
				}
			}
		}
	}

	public void changeAddressStatus(String address, boolean includeConsumer,
			boolean enable) {
		LOGGER.info("change address status : [" + address
				+ "], includeConsumer [" + includeConsumer + "], enable ["
				+ enable + "]");
		// 服务提供者地址
		List<Provider> providers = providerService.findByAddress(address);
		for (Provider provider : providers) {
			try {
				if (enable) {
					providerService.enableProvider(provider.getId());
				} else {
					// 本子系统是白名单，不能被禁用，否则可能导致无法管理，进而导致其他系统无法使用
					if (StringUtils.equals(provider.getApplication(),
							ApplicationContextHelper.getAppName())
							|| provider.getService().startsWith(
									"com.alibaba.dubbo")
							|| provider.getService().startsWith(
									"com.yeepay.g3.utils.soa")) {
						continue;
					}
					providerService.disableProvider(provider.getId());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		// 消费者
		if (includeConsumer) {
			List<Consumer> consumers = consumerService.findByAddress(address);
			for (Consumer consumer : consumers) {
				try {
					if (enable) {
						consumerFacade.allow(new Long[] { consumer.getId() },
								"system");
					} else {
						consumerFacade.forbid(new Long[] { consumer.getId() },
								"system");
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		RemoteCacheUtils.remove(SoaCenterConst.CACHE_ADDR_INFO);
	}

	/**
	 * 过滤应用列表为空的地址信息<br>
	 * 场景：机器部署的全是子系统（提供者），这些提供者其实引用了监控服务，也是消费者<br>
	 * 因查找provider地址的时候已处理，查找consumer地址时就跳过
	 *
	 * @param retAddressList
	 */
	private void filterBlankConsumer(List<AddressDTO> retAddressList) {
		for (int i = retAddressList.size() - 1; i >= 0; i--) {
			AddressDTO address = retAddressList.get(i);
			if (address.getAppList() == null || address.getAppList().isEmpty()) {
				retAddressList.remove(i);
			}
		}
	}
}
