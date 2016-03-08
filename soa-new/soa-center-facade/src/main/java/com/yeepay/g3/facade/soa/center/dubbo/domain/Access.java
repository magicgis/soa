package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 服务访问控制</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:47
 */
public class Access extends Entity {

	private static final long serialVersionUID = -962351722638094446L;

	/**
	 * 服务名
	 */
	private String service;

	/**
	 * 消费者地址
	 */
	private String address;

	/**
	 * 状态
	 */
	private boolean allow;

	/**
	 * 用户名
	 */
	private String username;

	public Access() {
	}

	public Access(Long id) {
		super(id);
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
