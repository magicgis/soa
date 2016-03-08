package com.yeepay.g3.facade.soa.center.param;

import java.io.Serializable;

/**
 * @author by menghao.chen
 */
public class ClientQueryParam implements Serializable {

	private static final long serialVersionUID = 8527446523788211466L;
	/**
	 * 客户机地址
	 */
	private String address;

	/**
	 * 服务器端口
	 */
	private Integer port;


	private Integer pageNo;

	private Integer pageSize;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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
