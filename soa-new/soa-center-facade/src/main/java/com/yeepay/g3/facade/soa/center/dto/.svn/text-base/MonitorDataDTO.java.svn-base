package com.yeepay.g3.facade.soa.center.dto;

import java.io.Serializable;

/**
 * Title: 监控数据<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-9 13:19
 */
public class MonitorDataDTO extends MonitorDataItemDTO implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	/**
	 * ID
	 */
	private Long id;

	/**
	 * 时间
	 */
	private String dateStr;

	/**
	 * 服务名称
	 */
	private String service;

	/**
	 * 方法
	 */
	private String method;

	private String type;

	/**
	 * 消费者IP
	 */
	private String consumer;

	/**
	 * 提供者IP
	 */
	private String provider;

	public MonitorDataDTO(String dateStr, String service, String method, String type, String consumer, String provider, int success, int failure, int elapsed, int concurrent, int maxElapsed, int maxConcurrent) {
		this.dateStr = dateStr;
		this.service = service;
		this.method = method;
		this.type = type;
		this.consumer = consumer;
		this.provider = provider;
		this.success = success;
		this.failure = failure;
		this.elapsed = elapsed;
		this.concurrent = concurrent;
		this.maxElapsed = maxElapsed;
		this.maxConcurrent = maxConcurrent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

}
