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
 * @author：wang.bao
 * @since：2014年7月28日 下午7:11:32
 * @version:
 */
public class AppUpgradeInfoDTO implements Serializable {
	private static final long serialVersionUID = 3960460745977130263L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 应用名
	 */
	private String appName;

	/**
	 * 升级信息
	 */
	private String upgradeInfo;

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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUpgradeInfo() {
		return upgradeInfo;
	}

	public void setUpgradeInfo(String upgradeInfo) {
		this.upgradeInfo = upgradeInfo;
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
