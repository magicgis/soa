package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.dubbo.service.impl.AbstractService;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.Pair;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.SyncUtils;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.utils.OverrideUtils;
import com.yeepay.g3.facade.soa.center.utils.Tool;

/**
 * Title: 服务提供者 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:53
 */
@Service
public class ProviderFacadeImpl implements ProviderFacade {

	@Autowired
	private ProviderService providerService;

	@Autowired
	private OverrideService overrideService;

	@java.lang.Override
	public void create(Provider provider) {
		providerService.create(provider);
	}

	@java.lang.Override
	public void enableProvider(Long id) {
		providerService.enableProvider(id);
	}

	@java.lang.Override
	public void disableProvider(Long id) {
		providerService.disableProvider(id);
	}

	@java.lang.Override
	public void doublingProvider(Long id) {
		providerService.doublingProvider(id);
	}

	@java.lang.Override
	public void halvingProvider(Long id) {
		providerService.halvingProvider(id);
	}

	@java.lang.Override
	public void deleteStaticProvider(Long id) {
		providerService.deleteStaticProvider(id);
	}

	@java.lang.Override
	public void deleteStaticProvider(Long[] ids) {
		for (Long id : ids) {
			Provider provider = providerService.findProvider(id);
			if (provider == null) {
				throw SOAException.PROVIDER_NOT_EXISTS_EXCEPTION
						.newInstance("不存在的服务提供者: {0}", id);
			} else if (provider.isDynamic()) {
				throw SOAException.CANNOT_DELETE_STATIC_PROVIDER_EXCEPTION
						.newInstance("不能删除动态服务提供者: {0}", id);
//			} else if (! super.currentUser.hasServicePrivilege(provider.getService())) {
//				throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", provider.getService());
			}
		}
		for (Long id : ids) {
			providerService.deleteStaticProvider(id);
		}
	}

	@java.lang.Override
	public void updateProvider(Provider provider) {
		providerService.updateProvider(provider);
	}

	@java.lang.Override
	public Provider findProvider(Long id) {
		return providerService.findProvider(id);
	}

	@java.lang.Override
	public Provider findProviderDetail(Long id) {
		Provider provider = providerService.findProvider(id);
		if (provider != null && provider.isDynamic()) {
			List<Override> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
			OverrideUtils.setProviderOverrides(provider, overrides);
		}
		return provider;
	}

	@java.lang.Override
	public List<String> findServices() {
		return providerService.findServices();
	}

	@java.lang.Override
	public List<String> findServicesSorted() {
		List<String> ret = this.findServices();
		return Tool.sortSimpleName(ret);
	}

	@java.lang.Override
	public List<String> findServicesByApplication(String application) {
		return providerService.findServicesByApplication(application);
	}

	@java.lang.Override
	public List<String> findServicesByAddress(String providerAddress) {
		List<String> ret = Lists.newArrayList();

		ConcurrentMap<String, Map<Long, URL>> providerUrls = ((AbstractService) providerService).getRegistryCache().get(Constants.PROVIDERS_CATEGORY);
		if (providerUrls == null || providerAddress == null || providerAddress.length() == 0) return ret;

		for (Map.Entry<String, Map<Long, URL>> e1 : providerUrls.entrySet()) {
			Map<Long, URL> value = e1.getValue();
			for (Map.Entry<Long, URL> e2 : value.entrySet()) {
				URL u = e2.getValue();
				if (providerAddress.equals(u.getAddress())) {
					ret.add(e1.getKey());
					break;
				}
			}
		}
		return ret;
	}

	@java.lang.Override
	public Set<String> findAddresses() {
		return Sets.newHashSet(providerService.findAddresses());
	}

	@java.lang.Override
	public Set<String> findAddressesByApplication(String application) {
		return Sets.newHashSet(providerService.findAddressesByApplication(application));
	}

	@java.lang.Override
	public Set<String> findAddressesByService(String serviceName) {
		Set<String> ret = Sets.newHashSet();
		ConcurrentMap<String, Map<Long, URL>> providerUrls = ((AbstractService) providerService).getRegistryCache().get(Constants.PROVIDERS_CATEGORY);
		if (null == providerUrls || providerUrls.get(serviceName) == null) return ret;

		for (Map.Entry<Long, URL> e2 : providerUrls.get(serviceName).entrySet()) {
			URL u = e2.getValue();
			String app = u.getAddress();
			if (app != null) ret.add(app);
		}
		return ret;
	}

	@java.lang.Override
	public Set<String> findApplications() {
		return Sets.newHashSet(providerService.findApplications());
	}

	@java.lang.Override
	public Set<String> findApplicationsByServiceName(String serviceName) {
		return Sets.newHashSet(providerService.findApplicationsByServiceName(serviceName));
	}

	@java.lang.Override
	public Set<String> findMethodsByService(String serviceName) {
		return Sets.newHashSet(providerService.findMethodsByService(serviceName));
	}

	@java.lang.Override
	public List<Provider> findByService(String serviceName) {
		List<Provider> providers = providerService.findByService(serviceName);
		for (Provider provider : providers) {
			provider.setWeight(getProviderWeight(provider));
		}
		DataConvertUtils.sort(providers, "address");
		return providers;
	}

	@java.lang.Override
	public List<Provider> findAll() {
		List<Provider> providers = providerService.findAll();
		for (Provider provider : providers) {
			if (provider != null) {
				provider.setWeight(getProviderWeight(provider));
				if (provider.isDynamic()) {
					List<Override> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
					OverrideUtils.setProviderOverrides(provider, overrides);
				}
			}
		}
		return providers;
	}

	@java.lang.Override
	public List<Provider> findAllSorted() {
		List<Provider> ret = this.findAll();
		return Tool.sortProviderBySimpleName(ret);
	}

	@java.lang.Override
	public List<Provider> findByAddress(String providerAddress) {
		List<Provider> providers = providerService.findByAddress(providerAddress);
		for (Provider provider : providers) {
			if (provider != null) {
				provider.setWeight(getProviderWeight(provider));
				if (provider.isDynamic()) {
					List<Override> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
					OverrideUtils.setProviderOverrides(provider, overrides);
				}
			}
		}
		DataConvertUtils.sort(providers, "service");
		return providers;
	}

	@java.lang.Override
	public List<Provider> findByApplication(String application) {
		List<Provider> providers = providerService.findByApplication(application);
		for (Provider provider : providers) {
			if (provider != null) {
				provider.setWeight(getProviderWeight(provider));
				if (provider.isDynamic()) {
					List<Override> overrides = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
					OverrideUtils.setProviderOverrides(provider, overrides);
				}
			}
		}
		DataConvertUtils.sort(providers, "service");
		return providers;
	}

	@java.lang.Override
	public Provider findByServiceAndAddress(String service, String address) {
		return SyncUtils.url2Provider(findProviderUrl(service, address));
	}

	private Pair<Long, URL> findProviderUrl(String service, String address) {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY);
		filter.put(SyncUtils.ADDRESS_FILTER_KEY, address);

		Map<Long, URL> ret = SyncUtils.filterFromCategory(((AbstractService) providerService).getRegistryCache(), filter);
		if (ret.isEmpty()) {
			return null;
		} else {
			Long key = ret.entrySet().iterator().next().getKey();
			return new Pair<Long, URL>(key, ret.get(key));
		}
	}

	@java.lang.Override
	public boolean isProviderEnabled(Provider provider) {
		List<Override> oList = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
		return Tool.isProviderEnabled(provider, oList);
	}

	@java.lang.Override
	public int getProviderWeight(Provider provider) {
		List<Override> oList = overrideService.findByServiceAndAddress(provider.getService(), provider.getAddress());
		return Tool.getProviderWeight(provider, oList);
	}

	@java.lang.Override
	public void doubling(Long[] ids, String operator, String operatorAddress) {
//		checkPrivilege(ids, operator);

		for (Long id : ids) {
			providerService.doublingProvider(id);
		}
	}

	@java.lang.Override
	public void halving(Long[] ids, String operator, String operatorAddress) {
		//		checkPrivilege(ids, operator);

		for (Long id : ids) {
			providerService.halvingProvider(id);
		}
	}

	@java.lang.Override
	public void forbid(Long[] ids, String operator, String operatorAddress) {
		//		checkPrivilege(ids, operator);

		for (Long id : ids) {
			providerService.disableProvider(id);
		}
	}

	@java.lang.Override
	public void allow(Long[] ids, String operator, String operatorAddress) {
		//		checkPrivilege(ids, operator);

		for (Long id : ids) {
			providerService.enableProvider(id);
		}
	}

	/**
	 * 权限检查
	 *
	 * @param ids      服务提供者编号
	 * @param operator 操作员
	 */
	@SuppressWarnings("unused")
	private void checkPrivilege(Long[] ids, String operator) {
		// TODO 权限控制
		for (Long id : ids) {
			Provider provider = providerService.findProvider(id);
			if (provider == null) {
				throw SOAException.PROVIDER_NOT_EXISTS_EXCEPTION
						.newInstance("不存在的服务提供者: {0}", id);
//			} else if (! super.currentUser.hasServicePrivilege(provider.getService())) {
//				throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", provider.getService());
			}
		}

//				if (!super.currentUser.hasServicePrivilege(w.getService())) {
//					throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", s);
//					return false;
//				}
	}
}
