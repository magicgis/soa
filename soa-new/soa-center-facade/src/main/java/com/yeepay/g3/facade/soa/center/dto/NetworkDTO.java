package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;

/**
 * 网络状态
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午5:27:12
 * @version:
 */
public class NetworkDTO implements Serializable {
	private static final long serialVersionUID = 9031165585416766460L;
	/**
	 * ID
	 */
	private Long id;
	/**
	 * 消费者IP地址
	 */
	private String consumer;

	/**
	 * 提供者IP地址
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

	/**
	 * 消费者应用列表
	 */
	private List<String> consumerAppList;

	/**
	 * 提供者应用列表
	 */
	private List<String> providerAppList;

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

	public List<String> getConsumerAppList() {
		return consumerAppList;
	}

	public void setConsumerAppList(List<String> consumerAppList) {
		this.consumerAppList = consumerAppList;
	}

	public List<String> getProviderAppList() {
		return providerAppList;
	}

	public void setProviderAppList(List<String> providerAppList) {
		this.providerAppList = providerAppList;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
