/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class AppOwnerDTO implements Serializable {
	private static final long serialVersionUID = -7803387907930119024L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 后台登录名
	 */
	private String loginName;

	/**
	 * 用户名
	 */
	private String ownerName;

	/**
	 * 创建日期
	 */
	private Date createDate;

	public AppOwnerDTO() {
	}

	public AppOwnerDTO(String appName, String loginName, String ownerName) {
		this.appName = appName;
		this.loginName = loginName;
		this.ownerName = ownerName;
	}

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
