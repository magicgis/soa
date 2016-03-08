/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.biz;

import com.alibaba.dubbo.common.URL;
import com.google.common.collect.Sets;
import com.yeepay.g3.core.soa.center.dubbo.service.ConsumerService;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.service.AppInfoService;
import com.yeepay.g3.core.soa.center.service.ServiceInfoService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.AppDependencyDTO;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.MethodInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.enums.AppRoleEnum;
import com.yeepay.g3.facade.soa.center.enums.AuthStatusEnum;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.cache.remote.RemoteCacheUtils;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.event.ext.BaseEventUtils;
import com.yeepay.g3.utils.rmi.utils.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author：wang.bao
 * @since：2014年8月13日 上午9:28:55
 * @version:
 */
@Component
public class RegistrySyncBiz {
	private final static Logger logger = LoggerFactory
			.getLogger(RegistrySyncBiz.class);

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private ServiceInfoService serviceInfoService;

	private static Set<String> dynamicCacheKeys = new HashSet<String>();

	public static void addDynamicCacheKey(String name) {
		dynamicCacheKeys.add(name);
	}

	@Transactional
	public void syncAppInfo() {
		logger.info("start sync app info!");
		Set<String> applications = new TreeSet<String>();
		Set<String> providerApplications = Sets.newHashSet(providerService
				.findApplications());
		if (providerApplications != null && providerApplications.size() > 0) {
			applications.addAll(providerApplications);
			for (String appName : providerApplications) {
				try {
					List<AppDependencyDTO> dependencyList = new ArrayList<AppDependencyDTO>();
					AppInfoDTO app = new AppInfoDTO();
					app.setAppName(appName);
					app.setStatus(SoaStatusEnum.ACTIVE);

					Set<String> deps = this.findDependencies(appName, false);
					Set<String> consumers = this
							.findDependencies(appName, true);
					Set<String> addresses = Sets.newHashSet(providerService
							.findAddressesByApplication(appName));
					// 部署机器数
					app.setAddressCount(addresses.size());
					// 依赖其他应用数
					app.setDepAppCount(deps.size());
					// 被引用数
					app.setDepByAppCount(consumers.size());
					// 暴露服务数
					app.setExpServiceCount(this.distinct(
							providerService.findServicesByApplication(appName))
							.size());
					// 引用服务数
					app.setRefServiceCount(this.distinct(
							consumerService.findServicesByApplication(appName))
							.size());
					app.setRole(this.calcAppRole(app));
					for (String consumerApp : consumers) {
						AppDependencyDTO dependency = new AppDependencyDTO();
						dependency.setAppName(consumerApp);
						dependency.setDepAppName(appName);
						dependencyList.add(dependency);
					}
					appInfoService.syncAppInfo(app);
					appInfoService.syncDependency(dependencyList);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
		}

		Set<String> consumerApplications = consumerService.findApplications();
		if (consumerApplications != null && consumerApplications.size() > 0) {
			for (String appName : consumerApplications) {
				try {
					// 提供者已处理过
					if (applications.contains(appName)) {
						continue;
					}
					List<AppDependencyDTO> dependencyList = new ArrayList<AppDependencyDTO>();
					AppInfoDTO app = new AppInfoDTO();
					app.setAppName(appName);
					app.setStatus(SoaStatusEnum.ACTIVE);

					Set<String> deps = this.findDependencies(appName, false);
					Set<String> addresses = consumerService
							.findAddressesByApplication(appName);
					// 部署机器数
					app.setAddressCount(addresses.size());
					// 依赖其他应用数
					app.setDepAppCount(deps.size());
					// 被引用数
					app.setDepByAppCount(0);
					// 暴露服务数
					app.setExpServiceCount(this.distinct(
							providerService.findServicesByApplication(appName))
							.size());
					// 引用服务数
					app.setRefServiceCount(this.distinct(
							consumerService.findServicesByApplication(appName))
							.size());
					app.setRole(this.calcAppRole(app));
					for (String providerApp : deps) {
						AppDependencyDTO dependency = new AppDependencyDTO();
						dependency.setAppName(appName);
						dependency.setDepAppName(providerApp);
						dependency.setAuthStatus(AuthStatusEnum.PUBLIC);
						dependencyList.add(dependency);
					}
					appInfoService.syncAppInfo(app);
					appInfoService.syncDependency(dependencyList);
				} catch (Exception e) {
					logger.error("", e);
				}
			}
			applications.addAll(consumerApplications);
		}
		// 当前未同步未发现的应用标记为宕机
		appInfoService.syncDownApp();
		// 同步服务信息
		for (String appName : providerApplications) {
			BaseEventUtils.sendEvent(SoaCenterConst.SYNC_SERVICE_INFO, appName);
		}
		logger.info("end sync app info!");
	}

	public void syncServiceInfo(String appName) {
		logger.info("start sync service info! appName=" + appName);
		if (StringUtils.isBlank(appName)) {
			return;
		}
		List<String> serviceList = providerService
				.findServicesByApplication(appName);
		Set<String> serviceSet = Sets.newHashSet();
		Map<String, Integer> addrCache = new HashMap<String, Integer>();
		Map<String, String> protocolCache = new HashMap<String, String>();
		serviceSet.addAll(serviceList);
		for (String service : serviceSet) {
			ServiceInfoDTO ser = new ServiceInfoDTO();
			ser.setAppName(appName);
			ser.setServiceInterface(service);
			ser.setServiceInterfaceLowcase(service.toLowerCase());
			ser.setStatus(SoaStatusEnum.ACTIVE);

			String protocol = protocolCache.get(appName);
			if (StringUtils.isBlank(protocol)) {
				List<Provider> providers = this.providerService
						.findByService(service);
				Set<String> protocols = this.findProtocol(providers);
				protocol = StringUtils.join(protocols, ",");
				protocolCache.put(appName, protocol);
			}
			ser.setServiceProtocol(protocol);

			Integer providerCount = addrCache.get(appName);
			if (providerCount == null) {
				Set<String> providerAddresses = Sets
						.newHashSet(this.providerService
								.findAddressesByService(service));
				providerCount = providerAddresses.size();
				addrCache.put(appName, providerCount);
			}
			ser.setProviderCount(providerCount);

			Set<String> consumerAddressess = this.consumerService
					.findAddressesByService(service);
			ser.setConsumerCount(consumerAddressess.size());
			serviceInfoService.syncServiceInfo(ser);
			BaseEventUtils.sendEvent(SoaCenterConst.SYNC_METHOD_INFO,
					ser.getServiceInterface(), ser.getId());
		}
		logger.info("end sync service info! appName=" + appName);
	}

	public void syncMethodInfo(String serviceName, Long serviceId) {
		if (serviceId == null && StringUtils.isBlank(serviceName)) {
			return;
		}
		if (serviceId == null) {
			ServiceInfoDTO ser = serviceInfoService.findService(serviceName);
			if (ser == null) {
				logger.info("service info not exist : serviceInterface : "
						+ serviceName);
				return;
			}
			serviceId = ser.getId();
		} else {
			ServiceInfoDTO ser = serviceInfoService.findService(serviceId);
			if (ser == null) {
				logger.info("service info not exist : serviceId : " + serviceId);
				return;
			}
			serviceName = ser.getServiceInterface();
		}
		Set<String> methodSet = Sets.newHashSet(providerService
				.findMethodsByService(serviceName));
		for (String method : methodSet) {
			MethodInfoDTO m = new MethodInfoDTO();
			m.setServiceId(serviceId);
			m.setMethodName(method);
			m.setStatus(SoaStatusEnum.ACTIVE);
			serviceInfoService.syncMethodInfo(m);
		}
	}

	private <T> Set<String> distinct(List<T> list, String... property) {
		Set<String> set = new HashSet<String>();
		for (T t : list) {
			if (t instanceof String) {
				set.add((String) t);
			} else {
				set.add((String) Reflections.getFieldValue(t, property[0]));
			}
		}
		return set;
	}

	private Set<String> findProtocol(List<Provider> providers) {
		Set<String> ret = Sets.newHashSet();
		for (Provider p : providers) {
			URL url = URL.valueOf(p.getUrl());
			ret.add(url.getProtocol());
		}
		return ret;
	}

	public AppRoleEnum calcAppRole(AppInfoDTO app) {
		if (app.getExpServiceCount() > 0 && app.getDepAppCount() > 0) {
			return AppRoleEnum.BOTH;
		} else if (app.getExpServiceCount() > 0) {
			return AppRoleEnum.PROVIDER;
		} else {
			return AppRoleEnum.CONSUMER;
		}
	}

	public void clearCache() {
		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey(SoaCenterConst.CACHE_APP_INFO));
		RemoteCacheUtils.remove(SoaCenterConst.CACHE_ADDR_INFO);
		if (CollectionUtils.isNotEmpty(dynamicCacheKeys)) {
			for (String key : dynamicCacheKeys) {
				RemoteCacheUtils.remove(key);
			}
			dynamicCacheKeys.clear();
		}
	}

	public Set<String> findAllApplications() {
		Set<String> applications = Sets.newHashSet();
		applications.addAll(providerService.findApplications());
		applications.addAll(consumerService.findApplications());
		return applications;
	}

	public Set<String> findDependencies(String application, boolean reverse) {
		if (reverse) {
			Set<String> dependencies = Sets.newHashSet();
			Set<String> services = Sets.newHashSet(providerService
					.findServicesByApplication(application));
			if (services != null && services.size() > 0) {
				for (String service : services) {
					Set<String> applications = consumerService
							.findApplicationsByServiceName(service);
					if (applications != null && applications.size() > 0) {
						dependencies.addAll(applications);
					}
				}
			}
			return dependencies;
		} else {
			Set<String> dependencies = Sets.newHashSet();
			Set<String> services = Sets.newHashSet(consumerService
					.findServicesByApplication(application));
			if (services != null && services.size() > 0) {
				for (String service : services) {
					Set<String> applications = Sets.newHashSet(providerService
							.findApplicationsByServiceName(service));
					if (applications != null && applications.size() > 0) {
						dependencies.addAll(applications);
					}
				}
			}
			return dependencies;
		}
	}

	public Set<String> findAllServices() {
		Set<String> services = Sets.newHashSet();
		services.addAll(providerService.findServices());
		services.addAll(consumerService.findServices());
		return services;
	}
}
