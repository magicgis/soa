package com.yeepay.g3.core.soa.center.dubbo.utils.sync;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.registry.NotifyListener;
import com.alibaba.dubbo.registry.RegistryService;
import com.google.common.collect.Lists;
import com.yeepay.g3.facade.soa.center.utils.Tool;

/**
 * @author ding.lid
 */
@Component
public class RegistryServerSync implements InitializingBean, DisposableBean, NotifyListener {

	private static final Logger logger = LoggerFactory.getLogger(RegistryServerSync.class);

	private static final URL SUBSCRIBE = new URL(Constants.ADMIN_PROTOCOL, NetUtils.getLocalHost(), 0, "",
			Constants.INTERFACE_KEY, Constants.ANY_VALUE,
			Constants.GROUP_KEY, Constants.ANY_VALUE,
			Constants.VERSION_KEY, Constants.ANY_VALUE,
			Constants.CLASSIFIER_KEY, Constants.ANY_VALUE,
			Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY + ","
			+ Constants.CONSUMERS_CATEGORY + ","
			+ Constants.ROUTERS_CATEGORY + ","
			+ Constants.CONFIGURATORS_CATEGORY,
			Constants.ENABLED_KEY, Constants.ANY_VALUE,
			Constants.CHECK_KEY, String.valueOf(false));

	/**
	 * 获取唯一ID，当然只是接近唯一 <br/>
	 * 返回hashCode+timestamp[last4]
	 * 
	 * @param url
	 * @return
	 */
	Long getUK(URL url) {
		StringBuilder sb = new StringBuilder();
		sb.append(url.hashCode());// 加入hashcode
		String timestamp = url.getParameter(Constants.TIMESTAMP_KEY);
		if (timestamp != null && timestamp.length() > 4) {
			// 加入时间戳后四位
			sb.append(timestamp.substring(timestamp.length() - 4));
		}
		return Long.valueOf(sb.toString());
	}

	@Autowired
	private RegistryService registryService;

	// ConcurrentMap<category, ConcurrentMap<servicename, Map<Long, URL>>>
	private final ConcurrentMap<String, ConcurrentMap<String, Map<Long, URL>>> registryCache = new ConcurrentHashMap<String, ConcurrentMap<String, Map<Long, URL>>>();

	public ConcurrentMap<String, ConcurrentMap<String, Map<Long, URL>>> getRegistryCache() {
		return registryCache;
	}

	public void afterPropertiesSet() throws Exception {
		logger.info("Init Dubbo Admin Sync Cache...");
		registryService.subscribe(SUBSCRIBE, this);
	}

	public void destroy() throws Exception {
		registryService.unsubscribe(SUBSCRIBE, this);
	}

	// 收到的通知对于 ，同一种类型数据（override、subcribe、route、其它是Provider），同一个服务的数据是全量的
	public void notify(List<URL> urls) {
		if (urls == null || urls.isEmpty()) {
			return;
		}
		// Map<category, Map<servicename, Map<Long, URL>>>
		final Map<String, Map<String, Map<Long, URL>>> categories = new HashMap<String, Map<String, Map<Long, URL>>>();
		List<URL> configs = Lists.newArrayList();
		for (URL url : urls) {
			if ("config".equalsIgnoreCase(url.getProtocol())) {
				configs.add(url);
				continue;
			}
			String category = url.getParameter(Constants.CATEGORY_KEY, Constants.PROVIDERS_CATEGORY);
			if (Constants.EMPTY_PROTOCOL.equalsIgnoreCase(url.getProtocol())) { // 注意：empty协议的group和version为*
				ConcurrentMap<String, Map<Long, URL>> services = registryCache.get(category);
				if (services != null) {
					String group = url.getParameter(Constants.GROUP_KEY);
					String version = url.getParameter(Constants.VERSION_KEY);
					// 注意：empty协议的group和version为*
					if (!Constants.ANY_VALUE.equals(group) && !Constants.ANY_VALUE.equals(version)) {
						services.remove(url.getServiceKey());
					} else {
						for (Map.Entry<String, Map<Long, URL>> serviceEntry : services.entrySet()) {
							String service = serviceEntry.getKey();
							if (Tool.getInterface(service).equals(url.getServiceInterface())
									&& (Constants.ANY_VALUE.equals(group) || StringUtils.isEquals(group, Tool.getGroup(service)))
									&& (Constants.ANY_VALUE.equals(version) || StringUtils.isEquals(version, Tool.getVersion(service)))) {
								services.remove(service);
							}
						}
					}
				}
			} else {
				Map<String, Map<Long, URL>> services = categories.get(category);
				if (services == null) {
					services = new HashMap<String, Map<Long, URL>>();
					categories.put(category, services);
				}
				String service = url.getServiceKey();
				Map<Long, URL> ids = services.get(service);
				if (ids == null) {
					ids = new HashMap<Long, URL>();
					services.put(service, ids);
				}
				ids.put(getUK(url), url);
			}
		}
		for (Map.Entry<String, Map<String, Map<Long, URL>>> categoryEntry : categories.entrySet()) {
			String category = categoryEntry.getKey();
			ConcurrentMap<String, Map<Long, URL>> services = registryCache.get(category);
			if (services == null) {
				services = new ConcurrentHashMap<String, Map<Long, URL>>();
				registryCache.put(category, services);
			}
			services.putAll(categoryEntry.getValue());
		}
		RegistryConfigSync.notify(configs);
	}

	public void register(URL url) {
		registryService.register(url);
	}

	public void unregister(URL url) {
		registryService.unregister(url);
	}

	public void unsubscribe(URL url) {
		registryService.unsubscribe(url, this);
	}
}
