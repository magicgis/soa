package com.yeepay.g3.core.soa.center.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;
import com.yeepay.g3.utils.persistence.Entity;

/**
 * 网络状态
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午5:27:12
 * @version:
 */
public class Network implements Entity<Long> {
	private static final long serialVersionUID = 896383239523229007L;

	/**
	 * ID
	 */
	private Long id;
	/**
	 * 消费者
	 */
	private String consumer;

	/**
	 * 提供者
	 */
	private String provider;

	/**
	 * 状态
	 */
	private NetworkStatusEnum status;

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

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public NetworkStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NetworkStatusEnum status) {
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
