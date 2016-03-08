package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 系统功能对象</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Feature extends Entity {

	private static final long serialVersionUID = 3246619851259746169L;

	private String name;

	private boolean enabled;

	private String username;

	public Feature() {
	}

	public Feature(Long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
