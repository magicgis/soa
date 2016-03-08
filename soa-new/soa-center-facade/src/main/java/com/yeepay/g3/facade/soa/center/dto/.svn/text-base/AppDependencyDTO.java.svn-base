package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.AuthStatusEnum;

/**
 * 应用依赖关系
 *
 * @author：wang.bao
 * @since：2014年7月28日 下午7:28:14
 * @version:
 */
public class AppDependencyDTO implements Serializable {
	private static final long serialVersionUID = -1754251516832260818L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 依赖应用名
	 */
	private String depAppName;

	/**
	 * 授权状态
	 */
	private AuthStatusEnum authStatus = AuthStatusEnum.PUBLIC;

	/**
	 * 创建日期
	 */
	private Date createDate = new Date();

	/**
	 * 最后修改日
	 */
	private Date lastModifyDate = new Date();

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

	public String getDepAppName() {
		return depAppName;
	}

	public void setDepAppName(String depAppName) {
		this.depAppName = depAppName;
	}

	public AuthStatusEnum getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(AuthStatusEnum authStatus) {
		this.authStatus = authStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
