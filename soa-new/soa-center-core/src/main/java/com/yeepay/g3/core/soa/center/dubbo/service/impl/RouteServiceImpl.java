package com.yeepay.g3.core.soa.center.dubbo.service.impl;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.yeepay.g3.core.soa.center.dubbo.service.RouteService;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.ParseUtils;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.RouteRule;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.Pair;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.SyncUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IbatisRouteService
 *
 * @author william.liangf
 */
@Component
public class RouteServiceImpl extends AbstractService implements RouteService {

	public void createRoute(Route route) {
		registryService.register(route.toUrl());
	}

	public void updateRoute(Route route) {
		Long id = route.getId();
		if (id == null) {
			throw new IllegalStateException("no route id");
		}
		URL oldRoute = findRouteUrl(id);
		if (oldRoute == null) {
			logger.warn("Route was changed! route id : " + id);
			return;
		}

		registryService.unregister(oldRoute);
		registryService.register(route.toUrl());
	}

	public void deleteRoute(Long id) {
		URL oldRoute = findRouteUrl(id);
		if (oldRoute == null) {
			logger.warn("Route was changed! route id : " + id);
			return;
		}
		registryService.unregister(oldRoute);
	}

	public void enableRoute(Long id) {
		if (id == null) {
			throw new IllegalStateException("no route id");
		}

		URL oldRoute = findRouteUrl(id);
		if (oldRoute == null) {
			logger.warn("Route was changed! route id : " + id);
			return;
		}
		if (oldRoute.getParameter("enabled", true)) {
			return;
		}

		registryService.unregister(oldRoute);
		URL newRoute = oldRoute.addParameter("enabled", true);
		registryService.register(newRoute);

	}

	public void disableRoute(Long id) {
		if (id == null) {
			throw new IllegalStateException("no route id");
		}

		URL oldRoute = findRouteUrl(id);
		if (oldRoute == null) {
			logger.warn("Route was changed! route id : " + id);
			return;
		}
		if (!oldRoute.getParameter("enabled", true)) {
			return;
		}

		registryService.unregister(oldRoute);
		URL newRoute = oldRoute.addParameter("enabled", false);
		registryService.register(newRoute);

	}

	public List<Route> findAll() {
		return SyncUtils.url2RouteList(findAllUrl());
	}

	private Map<Long, URL> findAllUrl() {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.ROUTERS_CATEGORY);

		return SyncUtils.filterFromCategory(getRegistryCache(), filter);
	}

	public Route findRoute(Long id) {
		return SyncUtils.url2Route(findRouteUrlPair(id));
	}

	public Pair<Long, URL> findRouteUrlPair(Long id) {
		return SyncUtils.filterFromCategory(getRegistryCache(),
				Constants.ROUTERS_CATEGORY, id);
	}

	private URL findRouteUrl(Long id) {
		Route r = findRoute(id);
		return r != null ? r.toUrl() : null;
	}

	private Map<Long, URL> findRouteUrl(String service, String address,
			boolean force) {
		Map<String, String> filter = new HashMap<String, String>();
		filter.put(Constants.CATEGORY_KEY, Constants.ROUTERS_CATEGORY);
		if (service != null && service.length() > 0) {
			filter.put(SyncUtils.SERVICE_FILTER_KEY, service);
		}
		if (address != null && address.length() > 0) {
			filter.put(SyncUtils.ADDRESS_FILTER_KEY, address);
		}
		if (force) {
			filter.put("force", "true");
		}
		return SyncUtils.filterFromCategory(getRegistryCache(), filter);
	}

	public List<Route> findByService(String serviceName) {
		return SyncUtils.url2RouteList(findRouteUrl(serviceName, null, false));
	}

	public List<Route> findByAddress(String address) {
		return SyncUtils.url2RouteList(findRouteUrl(null, address, false));
	}

	public List<Route> findByServiceAndAddress(String service, String address) {
		return SyncUtils.url2RouteList(findRouteUrl(service, address, false));
	}

	public List<Route> findForceRouteByService(String service) {
		return SyncUtils.url2RouteList(findRouteUrl(service, null, true));
	}

	public List<Route> findForceRouteByAddress(String address) {
		return SyncUtils.url2RouteList(findRouteUrl(null, address, true));
	}

	public List<Route> findForceRouteByServiceAndAddress(String service,
			String address) {
		return SyncUtils.url2RouteList(findRouteUrl(service, address, true));
	}

	public List<Route> findAllForceRoute() {
		return SyncUtils.url2RouteList(findRouteUrl(null, null, true));
	}

	public boolean isInBlackList(Consumer consumer) {
		String service = consumer.getService();
		List<Route> routes = this.findForceRouteByService(service);
		if (routes == null || routes.size() == 0) {
			return false;
		}
		String ip = Tool.getIP(consumer.getAddress());
		for (Route route : routes) {
			try {
				if (!route.isEnabled()) {
					continue;
				}
				String filterRule = route.getFilterRule();
				if (filterRule == null || filterRule.length() == 0
						|| "false".equals(filterRule)) {
					Map<String, RouteRule.MatchPair> rule = RouteRule
							.parseRule(route.getMatchRule());
					RouteRule.MatchPair pair = rule.get("consumer.host");
					if (pair == null) {
						pair = rule.get("host");
					}
					if (pair != null) {
						if (pair.getMatches() != null
								&& pair.getMatches().size() > 0) {
							for (String host : pair.getMatches()) {
								if (ParseUtils.isMatchGlobPattern(host, ip)) {
									return true;
								}
							}
						}
						if (pair.getUnmatches() != null
								&& pair.getUnmatches().size() > 0) {
							boolean forbid = true;
							for (String host : pair.getUnmatches()) {
								if (ParseUtils.isMatchGlobPattern(host, ip)) {
									forbid = false;
								}
							}
							if (forbid) {
								return true;
							}
						}
					}
				}
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return false;
	}

}
