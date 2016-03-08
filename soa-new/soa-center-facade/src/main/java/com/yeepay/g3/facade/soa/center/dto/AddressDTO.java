/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alibaba.dubbo.common.Constants;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;

/**
 * 机器信息
 *
 * @author：wang.bao
 * @since：2014年8月28日 上午10:08:35
 * @version:
 */
public class AddressDTO implements Serializable {
	private static final long serialVersionUID = -1986017647673539050L;

	/**
	 * 机器
	 */
	private String address;

	/**
	 * 所属环境
	 */
	private String environment;

	/**
	 * 所属角色（控制闲时内测忙时对外）
	 */
	private AddressRoleEnum role = AddressRoleEnum.OUT;

	/**
	 * 应用列表
	 */
	private Set<String> appList;

	/**
	 * 应用详情列表
	 */
	private List<AppInfoDTO> appDetailList;

	private String side = Constants.PROVIDER;

	private SoaStatusEnum status = SoaStatusEnum.ACTIVE;

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

	public AddressRoleEnum getRole() {
		return role;
	}

	public void setRole(AddressRoleEnum role) {
		this.role = role;
	}

	public Set<String> getAppList() {
		return appList;
	}

	public void setAppList(Set<String> appList) {
		this.appList = appList;
	}

	public List<AppInfoDTO> getAppDetailList() {
		return appDetailList;
	}

	public void setAppDetailList(List<AppInfoDTO> appDetailList) {
		this.appDetailList = appDetailList;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public SoaStatusEnum getStatus() {
		return status;
	}

	public void setStatus(SoaStatusEnum status) {
		this.status = status;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
