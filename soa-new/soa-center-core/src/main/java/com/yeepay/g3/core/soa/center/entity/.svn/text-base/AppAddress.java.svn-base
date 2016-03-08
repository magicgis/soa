package com.yeepay.g3.core.soa.center.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.utils.persistence.Entity;

/**
 * 应用-机器关系
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:11:09
 * @version:
 */
public class AppAddress implements Entity<Long> {
	private static final long serialVersionUID = -5682648661088970862L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 所在机器
	 */
	private String address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
