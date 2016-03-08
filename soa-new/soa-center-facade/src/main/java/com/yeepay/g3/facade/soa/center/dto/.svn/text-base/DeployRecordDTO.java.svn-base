/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 应用发布（机器重启）记录
 *
 * @author：wang.bao
 * @since：2015年12月9日 下午4:04:04
 * @version:
 */
public class DeployRecordDTO implements Serializable {
	private static final long serialVersionUID = 5509606127447989169L;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * IP地址
	 */
	private String address;

	/**
	 * 操作员
	 */
	private String operator;

	/**
	 * 发起请求的IP地址
	 */
	private String reqAddress;

	/**
	 * 部署时间
	 */
	private Date deployTime;

	/**
	 * 恢复时间
	 */
	private Date recoverTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getReqAddress() {
		return reqAddress;
	}

	public void setReqAddress(String reqAddress) {
		this.reqAddress = reqAddress;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}

	public Date getRecoverTime() {
		return recoverTime;
	}

	public void setRecoverTime(Date recoverTime) {
		this.recoverTime = recoverTime;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
