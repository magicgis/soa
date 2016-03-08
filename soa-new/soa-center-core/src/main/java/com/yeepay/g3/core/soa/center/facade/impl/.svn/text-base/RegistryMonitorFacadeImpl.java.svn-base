package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.support.AbstractRegistry;
import com.alibaba.dubbo.registry.support.AbstractRegistryFactory;
import com.yeepay.g3.core.soa.center.biz.RegistrySyncBiz;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.RegistryDTO;
import com.yeepay.g3.facade.soa.center.facade.RegistryMonitorFacade;
import com.yeepay.g3.facade.soa.center.param.RegisteredQueryParam;
import com.yeepay.g3.facade.soa.center.param.SubscribedQueryParam;
import com.yeepay.g3.utils.cache.remote.RemoteCacheUtils;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author by menghao.chen
 */
@Service
public class RegistryMonitorFacadeImpl implements RegistryMonitorFacade {
	@Override
	public List<RegistryDTO> queryRegistry() {
		List<RegistryDTO> result = new ArrayList<RegistryDTO>();
		Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
		if (CollectionUtils.isNotEmpty(registries)) {
			for (Registry registry : registries) {
				RegistryDTO registryDTO = new RegistryDTO();
				String address = registry.getUrl().getAddress();
				String server = NetUtils.getHostName(address) + "/" + address;
				int registeredSize = 0;
				int subscribedSize = 0;
				if (registry instanceof AbstractRegistry) {
					registeredSize = ((AbstractRegistry) registry).getRegistered().size();
					subscribedSize = ((AbstractRegistry) registry).getSubscribed().size();
				}
				registryDTO.setAddress(address);
				registryDTO.setServer(server);
				registryDTO.setRegisteredSize(registeredSize);
				registryDTO.setSubscribedSize(subscribedSize);
				registryDTO.setAvailable(registry.isAvailable());
				result.add(registryDTO);
			}
		}
		return result;
	}

	@Override
	public PageList queryRegistered(RegisteredQueryParam queryParam) {
		PageList ret;
		List<String> result;
		String registry = queryParam.getRegistry();
		CheckUtils.notEmpty(registry, "registry");
		String cacheKey = SoaCenterConst.CACHE_REGISTERED_PREFIX + registry;
		List<String> cacheServices = (List<String>) RemoteCacheUtils.get(cacheKey);
		if (CollectionUtils.isNotEmpty(cacheServices)) {
			result = filterServiceWithAddress(cacheServices, queryParam.getAddress());
		} else {
			List<String> services = this.queryRegistered(registry);
			RegistrySyncBiz.addDynamicCacheKey(cacheKey);
			RemoteCacheUtils.put(cacheKey, services);
			result = filterServiceWithAddress(services, queryParam.getAddress());
		}
		ret = DataConvertUtils.cutToPageList(result,
				queryParam.getPageNo(), queryParam.getPageSize());
		return ret;
	}

	@Override
	public PageList querySubscribed(SubscribedQueryParam queryParam) {
		PageList ret;
		List<String> result;
		String registry = queryParam.getRegistry();
		CheckUtils.notEmpty(registry, "registry");
		String cacheKey = SoaCenterConst.CACHE_SUBSCRIBED_PREFIX + registry;
		List<String> cacheServices = (List<String>) RemoteCacheUtils.get(cacheKey);
		if (CollectionUtils.isNotEmpty(cacheServices)) {
			result = filterServiceWithAddress(cacheServices, queryParam.getAddress());
		} else {
			List<String> services = this.querySubscribed(registry);
			RegistrySyncBiz.addDynamicCacheKey(cacheKey);
			RemoteCacheUtils.put(cacheKey, services);
			result = filterServiceWithAddress(services, queryParam.getAddress());
		}
		ret = DataConvertUtils.cutToPageList(result,
				queryParam.getPageNo(), queryParam.getPageSize());
		return ret;
	}

	private List<String> querySubscribed(String registryAddress) {
		List<String> result = new ArrayList<String>();
		Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
		Registry registry = null;
		if (CollectionUtils.isNotEmpty(registries)) {
			if (registries.size() == 1) {
				registry = registries.iterator().next();
			} else {
				for (Registry r : registries) {
					String sp = r.getUrl().getAddress();
					if (((registryAddress == null || registryAddress.length() == 0) && registry == null)
							|| registryAddress.equals(sp)) {
						registry = r;
					}
				}
			}
		}
		if (registry != null && registry instanceof AbstractRegistry) {
			Set<URL> services = ((AbstractRegistry) registry).getSubscribed().keySet();
			if (services != null && services.size() > 0) {
				for (URL u : services) {
					result.add(u.toFullString().replace("<", "&lt;").replace(">", "&gt;"));
				}
			}
		}
		return result;
	}


	private List<String> queryRegistered(String registryAddress) {
		List<String> result = new ArrayList<String>();
		Collection<Registry> registries = AbstractRegistryFactory.getRegistries();
		Registry registry = null;
		if (CollectionUtils.isNotEmpty(registries)) {
			if (registries.size() == 1) {
				registry = registries.iterator().next();
			} else {
				for (Registry r : registries) {
					String sp = r.getUrl().getAddress();
					if (((registryAddress == null || registryAddress.length() == 0) && registry == null)
							|| registryAddress.equals(sp)) {
						registry = r;
					}
				}
			}
		}
		if (registry != null && registry instanceof AbstractRegistry) {
			Set<URL> services = ((AbstractRegistry) registry).getRegistered();
			if (services != null && services.size() > 0) {
				for (URL u : services) {
					result.add(u.toFullString().replace("<", "&lt;").replace(">", "&gt;"));
				}
			}
		}
		return result;
	}

	private List<String> filterServiceWithAddress(List<String> services, String address) {
		List<String> result = null;
		if (CollectionUtils.isNotEmpty(services)) {
			if (StringUtils.isNotBlank(address)) {
				result = new ArrayList<String>();
				for (String item : services) {
					if (item != null && item.contains(address)) {
						result.add(item);
					}
				}
			} else {
				result = services;
			}
		}
		return result;
	}
}