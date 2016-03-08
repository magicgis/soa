package com.yeepay.g3.facade.soa.center.dubbo.domain;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: 消费者</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Consumer extends Entity {

	private static final long serialVersionUID = -1140894843784583237L;

	/**
	 * 消费者所引用的服务名称
	 */
	private String service;

	private String parameters;

	/**
	 * 路由结果
	 */
	private String result;

	/**
	 * 消费者地址
	 */
	private String address;

	/**
	 * 消费者连接的注册中心地址
	 */
	private String registry;

	/**
	 * 应用名
	 */
	private String application;

	/**
	 * 消费者用户名
	 */
	private String username;

	/**
	 * 服务调用统计信息
	 */
	private String statistics;

	/**
	 * 服务调用统计时间
	 */
	private Date collected;

	private Override override;

	private List<Override> overrides;

	private List<Route> routes;

	private List<Provider> providers;

	/**
	 * 过期时间
	 */
	private Date expired;

	/**
	 * 存活时间，单位秒
	 */
	private long alived;

	/**
	 * 是否禁用
	 */
	private boolean forbid;

	/**
	 * 降级类型
	 */
	private String mock;

	public Consumer() {
	}

	public Consumer(Long id) {
		super(id);
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public Date getCollected() {
		return collected;
	}

	public void setCollected(Date collected) {
		this.collected = collected;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public Date getExpired() {
		return expired;
	}


	public void setExpired(Date expired) {
		this.expired = expired;
	}


	public long getAlived() {
		return alived;
	}


	public void setAlived(long alived) {
		this.alived = alived;
	}

	public Override getOverride() {
		return override;
	}

	public void setOverride(Override override) {
		this.override = override;
	}

	public List<Override> getOverrides() {
		return overrides;
	}

	public void setOverrides(List<Override> overrides) {
		this.overrides = overrides;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public List<Provider> getProviders() {
		return providers;
	}

	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}

	public boolean isForbid() {
		return forbid;
	}

	public void setForbid(boolean forbid) {
		this.forbid = forbid;
	}

	public String getMock() {
		return mock;
	}

	public void setMock(String mock) {
		this.mock = mock;
	}

	public URL toUrl() {
		String group = null;
		String version = null;
		String path = service;
		int i = path.indexOf("/");
		if (i > 0) {
			group = path.substring(0, i);
			path = path.substring(i + 1);
		}
		i = path.lastIndexOf(":");
		if (i > 0) {
			version = path.substring(i + 1);
			path = path.substring(0, i);
		}
		Map<String, String> param = StringUtils.parseQueryString(parameters);
		param.put(Constants.CATEGORY_KEY, Constants.CONSUMERS_CATEGORY);
		if (group != null) {
			param.put(Constants.GROUP_KEY, group);
		}
		if (version != null) {
			param.put(Constants.VERSION_KEY, version);
		}
		return URL.valueOf(Constants.CONSUMER_PROTOCOL + "://" + address + "/" + path
				+ "?" + StringUtils.toQueryString(param));
	}

}
