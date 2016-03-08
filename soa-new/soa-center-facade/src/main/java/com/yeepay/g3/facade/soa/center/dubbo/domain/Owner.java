package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 负责人实体</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:47
 */
public class Owner extends Entity {

	private static final long serialVersionUID = -4891350118145794727L;

	/**
	 * 可以包含通配符
	 */
	private String service;

	private String username;

	private User user;

	public Owner() {
	}

	public Owner(Long id) {
		super(id);
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
