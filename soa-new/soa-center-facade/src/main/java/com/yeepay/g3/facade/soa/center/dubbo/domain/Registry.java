package com.yeepay.g3.facade.soa.center.dubbo.domain;

import java.util.Date;

/**
 * <p>Title: Registry</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Registry extends Entity {

	private static final long serialVersionUID = -8866645978415551309L;

	private String registry;/* 注册中心地址 */

	private String url;

	private int connections = 0;/*注册中心连接数*/

	private Date expired;   /*过期时间*/

	private long alived;

	public Registry() {
	}

	public Registry(Long id) {
		super(id);
	}

	public Registry(String registryAddress, String consoleUrl, int aliveSeconds) {
		this.registry = registryAddress;
		this.url = consoleUrl;
		this.alived = aliveSeconds;
	}

	public String getAddress() {
		return registry;
	}

	public void setAddress(String registry) {
		this.registry = registry;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expiredDate) {
		this.expired = expiredDate;
	}

	public long getAlived() {
		return alived;
	}

	public void setAlived(long aliveSeconds) {
		this.alived = aliveSeconds;
	}


	public int getConnections() {
		return connections;
	}


	public void setConnections(int connections) {
		this.connections = connections;
	}

}
