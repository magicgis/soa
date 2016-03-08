package com.yeepay.g3.facade.soa.center.param;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;

/**
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:56:29
 * @version:
 */
public class NetworkQueryParam implements Serializable {
	private static final long serialVersionUID = -4941806023530725086L;
	/**
	 * IP地址
	 */
	private String address;

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 类型：all：全部；consumer：消费者；provider：提供者
	 */
	private String type;

	/**
	 * 状态
	 */
	private NetworkStatusEnum status;

	private Integer pageNo;

	private Integer pageSize;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getType() {
		if (type == null || type.trim().length() == 0) {
			type = "all";
		} else {
			type = type.toLowerCase();
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public NetworkStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NetworkStatusEnum status) {
		this.status = status;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
