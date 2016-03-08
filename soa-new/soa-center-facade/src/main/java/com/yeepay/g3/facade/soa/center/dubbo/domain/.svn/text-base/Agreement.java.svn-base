package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 指定应用的服务质量等级协定(SLA)对象</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:47
 */
public class Agreement extends Entity {

	private static final long serialVersionUID = -4888604682731513790L;

	/**
	 * 服务名
	 */
	private String service;

	/**
	 * 服务消费者应用名
	 */
	private String consumerApplication;

	/**
	 * 一天调用量
	 */
	private long invocationQuantity;

	/**
	 * TPS上限
	 */
	private int tps;

	/**
	 * 响应时间，毫秒
	 */
	private int responseTime;

	/**
	 * 可用率
	 */
	private double availability;

	private String username;

	public Agreement() {
	}

	public Agreement(Long id) {
		super(id);
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getConsumerApplication() {
		return consumerApplication;
	}

	public void setConsumerApplication(String consumerApplication) {
		this.consumerApplication = consumerApplication;
	}

	public long getInvocationQuantity() {
		return invocationQuantity;
	}

	public void setInvocationQuantity(long invocationQuantity) {
		this.invocationQuantity = invocationQuantity;
	}

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public double getAvailability() {
		return availability;
	}

	public void setAvailability(double availability) {
		this.availability = availability;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
