package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 负载均衡</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class LoadBalance extends Entity {

	private static final long serialVersionUID = -6050324375352581440L;

	public static final String ALL_METHOD = "*";

	/**
	 * 服务名称
	 */
	private String service;

	/**
	 * 方法名称
	 */
	private String method;

	/**
	 * 负载均衡策略
	 */
	private String strategy;

	/**
	 * 用户名
	 */
	private String username;

	public LoadBalance() {
	}

	public LoadBalance(Long id) {
		super(id);
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
