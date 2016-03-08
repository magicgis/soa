package com.yeepay.g3.facade.soa.center.param;

import java.io.Serializable;

/**
 * @author by menghao.chen
 */
public class RegisteredQueryParam implements Serializable {
	private static final long serialVersionUID = -8662256295238832874L;

	private String registry;

	private String address;

	private Integer pageNo;

	private Integer pageSize;

	public String getRegistry() {
		return registry;
	}

	public void setRegistry(String registry) {
		this.registry = registry;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPageNo() {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
