/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;

/**
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class MethodInfoDTO implements Serializable {
	private static final long serialVersionUID = 5271962394711744010L;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 服务ID
	 */
	private Long serviceId;

	/**
	 * 方法名
	 */
	private String methodName;

	/**
	 * 方法描述
	 */
	private String methodDesc;

	/**
	 * 方法签名
	 */
	private String methodSign;

	/**
	 * 状态
	 */
	private SoaStatusEnum status;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 最后更新日期
	 */
	private Date lastModifyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}

	public String getMethodSign() {
		return methodSign;
	}

	public void setMethodSign(String methodSign) {
		this.methodSign = methodSign;
	}

	public SoaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SoaStatusEnum status) {
		this.status = status;
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
