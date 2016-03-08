package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;

/**
 * @author by menghao.chen
 */
public class RegistryDTO implements Serializable {
	private static final long serialVersionUID = 5349112973823037019L;

	private String address;

	private String server;

	private Integer registeredSize;

	private Integer subscribedSize;

	private Boolean available;


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Integer getRegisteredSize() {
		return registeredSize;
	}

	public void setRegisteredSize(Integer registeredSize) {
		this.registeredSize = registeredSize;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Integer getSubscribedSize() {
		return subscribedSize;
	}

	public void setSubscribedSize(Integer subscribedSize) {
		this.subscribedSize = subscribedSize;
	}
}
