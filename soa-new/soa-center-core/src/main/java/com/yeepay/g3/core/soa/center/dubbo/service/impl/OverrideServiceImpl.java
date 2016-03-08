package com.yeepay.g3.core.soa.center.dubbo.service.impl;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.Pair;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.SyncUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IbatisOverrideDAO.java
 *
 * @author tony.chenl
 */
@Component
public class OverrideServiceImpl extends AbstractService implements
		OverrideService {

	public void saveOverride(Override override) {
		URL url = getUrlFromOverride(override);
		registryService.register(url);
	}

	public void updateOverride(Override override) {
		Long id = override.getId();
		if (id == null) {
			throw new IllegalStateException("no override id");
		}
		URL oldOverride = findOverrideUrl(id);
		if (oldOverride == null) {
			logger.warn("Override was changed! override id : " + id);
			return;
		}
		URL newOverride = getUrlFromOverride(override);

		registryService.unregister(oldOverride);
		registryService.register(newOverride);
	}

	public void deleteOverride(Long id) {
		URL oldOverride = findOverrideUrl(id);
		if (oldOverride == null) {
			logger.warn("Override was changed! override id : " + id);
			return;
		}
		registryService.unregister(oldOverride);
	}

	public void enableOverride(Long id) {
		if (id == null) {
			throw new IllegalStateException("no override id");
		}

		URL oldOverride = findOverrideUrl(id);
		if (oldOverride == null) {
			logger.warn("Override was changed! override id : " + id);
			return;
		}
		if (oldOverride.getParameter("enabled", true)) {
			return;
		}

		registryService.unregister(oldOverride);
		URL newOverride = oldOverride.addParameter("enabled", true);
		registryService.register(newOverride);
	}

	public void disableOverride(Long id) {
		if (id == null) {
			throw new IllegalStateException("no override id");
		}

		URL oldOverride = findOverrideUrl(id);
		if (oldOverride == null) {
			logger.warn("Override was changed! override id : " + id);
			return;
		}
		if (!oldOverride.getParameter("enabled", true)) {
			return;
		}

		registryService.unregister(oldOverride);
		URL newOverride = oldOverride.addParameter("enabled", false);
		registryService.register(newOverride);
	}

	private Map<Long, URL> findOverrideUrl(String service, String address,
			String application) {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.CONFIGURATORS_CATEGORY);
		if (service != null && service.length() > 0) {
			filter.put(SyncUtils.SERVICE_FILTER_KEY, service);
		}
		if (address != null && address.length() > 0) {
			filter.put(SyncUtils.ADDRESS_FILTER_KEY, address);
		}
		if (application != null && application.length() > 0) {
			filter.put(Constants.APPLICATION_KEY, application);
		}
		return SyncUtils.filterFromCategory(getRegistryCache(), filter);
	}

	public List<Override> findByAddress(String address) {
		return SyncUtils.url2OverrideList(findOverrideUrl(null, address, null));
	}

	public List<Override> findByServiceAndAddress(String service, String address) {
		return SyncUtils.url2OverrideList(findOverrideUrl(service, address,
				null));
	}

	public List<Override> findByApplication(String application) {
		return SyncUtils.url2OverrideList(findOverrideUrl(null, null,
				application));
	}

	public List<Override> findByService(String service) {
		return SyncUtils.url2OverrideList(findOverrideUrl(service, null, null));
	}

	public List<Override> findByServiceAndApplication(String service,
			String application) {
		return SyncUtils.url2OverrideList(findOverrideUrl(service, null,
				application));
	}

	public List<Override> findAll() {
		return SyncUtils.url2OverrideList(findOverrideUrl(null, null, null));
	}

	private Pair<Long, URL> findOverrideUrlPair(Long id) {
		return SyncUtils.filterFromCategory(getRegistryCache(),
				Constants.CONFIGURATORS_CATEGORY, id);
	}

	public Override findById(Long id) {
		return SyncUtils.url2Override(findOverrideUrlPair(id));
	}

	private URL getUrlFromOverride(Override override) {
		return override == null ? null : override.toUrl();
	}

	private URL findOverrideUrl(Long id) {
		return getUrlFromOverride(findById(id));
	}

}
