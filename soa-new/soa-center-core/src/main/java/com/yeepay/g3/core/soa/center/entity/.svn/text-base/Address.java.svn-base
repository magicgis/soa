package com.yeepay.g3.core.soa.center.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.persistence.Entity;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;

/**
 * 机器信息
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午5:25:27
 * @version:
 */
public class Address implements Entity<Long> {
	private static final long serialVersionUID = -7788874020859925392L;

	/**
	 * ID
	 */
	private Long id;
	/**
	 * IP地址
	 */
	private String address;

	/**
	 * 环境
	 */
	private String environment;

	/**
	 * 状态
	 */
	private SoaStatusEnum status;

	/**
	 * 运行时角色
	 */
	private AddressRoleEnum role;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 最后修改日
	 */
	private Date lastModifyDate;

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

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public SoaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SoaStatusEnum status) {
		this.status = status;
	}

	public AddressRoleEnum getRole() {
		return role;
	}

	public void setRole(AddressRoleEnum role) {
		this.role = role;
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
