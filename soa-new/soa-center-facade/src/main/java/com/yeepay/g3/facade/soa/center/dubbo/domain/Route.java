package com.yeepay.g3.facade.soa.center.dubbo.domain;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;

import java.util.List;

/**
 * <p>Title: Route</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Route extends Entity {

	private static final long serialVersionUID = -7630589008164140656L;

	public static final String ALL_METHOD = "*";

	// WHEN KEY

	public static final String KEY_METHOD = "method";

	public static final String KEY_CONSUMER_APPLICATION = "consumer.application";

	public static final String KEY_CONSUMER_GROUP = "consumer.cluster";

	public static final String KEY_CONSUMER_VERSION = "consumer.version";

	public static final String KEY_CONSUMER_HOST = "consumer.host";

	public static final String KEY_CONSUMER_METHODS = "consumer.methods";

	// THEN KEY

	public static final String KEY_PROVIDER_APPLICATION = "provider.application";

	public static final String KEY_PROVIDER_GROUP = "provider.cluster";

	public static final String KEY_PROVIDER_PROTOCOL = "provider.protocol";

	public static final String KEY_PROVIDER_VERSION = "provider.version";

	public static final String KEY_PROVIDER_HOST = "provider.host";

	public static final String KEY_PROVIDER_PORT = "provider.port";

	private long parentId; //默认为0

	private String name;

	private String service;

	private String rule;

	private String matchRule;

	private String filterRule;

	private int priority;

	private String username;

	private boolean enabled;

	private boolean force;

	private List<Route> children;

	public Route() {
	}

	public Route(Long id) {
		super(id);
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public List<Route> getChildren() {
		return children;
	}

	public void setChildren(List<Route> subRules) {
		this.children = subRules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
		String[] rules = rule.split(" => ");
		if (rules.length != 2) {
			throw new IllegalArgumentException("Illegal Route Condition Rule");
		}
		this.matchRule = rules[0];
		this.filterRule = rules[1];
	}

	public String getMatchRule() {
		return matchRule;
	}

	public void setMatchRule(String matchRule) {
		this.matchRule = matchRule;
	}

	public String getFilterRule() {
		return filterRule;
	}

	public void setFilterRule(String filterRule) {
		this.filterRule = filterRule;
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
		return URL.valueOf(Constants.ROUTE_PROTOCOL + "://" + Constants.ANYHOST_VALUE + "/" + path
				+ "?" + Constants.CATEGORY_KEY + "=" + Constants.ROUTERS_CATEGORY
				+ "&router=condition&runtime=false&enabled=" + isEnabled() + "&priority=" + getPriority() + "&force=" + isForce() + "&dynamic=false"
				+ "&name=" + getName() + "&" + Constants.RULE_KEY + "=" + URL.encode(getMatchRule() + " => " + getFilterRule())
				+ (group == null ? "" : "&" + Constants.GROUP_KEY + "=" + group)
				+ (version == null ? "" : "&" + Constants.VERSION_KEY + "=" + version));
	}

}
