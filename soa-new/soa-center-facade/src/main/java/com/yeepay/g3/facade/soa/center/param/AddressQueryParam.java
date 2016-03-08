package com.yeepay.g3.facade.soa.center.param;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.soa.common.AddressRoleEnum;

/**
 *
 * @author：wang.bao
 * @since：2014年8月28日 下午2:34:26
 * @version:
 */
public class AddressQueryParam implements Serializable {
	private static final long serialVersionUID = 30753020743203192L;

	private String address;

	private String appName;

	private String serviceName;

	private String side;

	private String environment;

	private SoaStatusEnum status;

	private AddressRoleEnum role;

	private Integer pageNo;

	private Integer pageSize;

	private boolean withDetail;

	/**
	 * 是否带缓存
	 */
	private boolean withCache;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (StringUtils.isNotBlank(address)) {
			if (!address.startsWith("*")) {
				this.address = "*" + address;
			}
			if (!address.endsWith("*")) {
				this.address += "*";
			}
		}
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		if (StringUtils.isNotBlank(appName)) {
			if (!appName.startsWith("*")) {
				this.appName = "*" + appName;
			}
			if (!appName.endsWith("*")) {
				this.appName += "*";
			}
		}
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		if (StringUtils.isNotBlank(serviceName)) {
			if (!serviceName.startsWith("*")) {
				this.serviceName = "*" + serviceName;
			}
			if (!serviceName.endsWith("*")) {
				this.serviceName += "*";
			}
		}
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
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

	public boolean isWithDetail() {
		return withDetail;
	}

	public void setWithDetail(boolean withDetail) {
		this.withDetail = withDetail;
	}

	public boolean isWithCache() {
		return withCache;
	}

	public void setWithCache(boolean withCache) {
		this.withCache = withCache;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
