package com.yeepay.g3.facade.soa.center.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Title: 监控数据统计结果<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 17:54
 */
public class MonitorStatisticsDataDTO implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	/**
	 * 服务
	 */
	private String service;

	/**
	 * 方法
	 */
	private String method;

	/**
	 * 消费者IP
	 */
	private String consumer;

	/**
	 * 提供者IP
	 */
	private String provider;

	/**
	 * 累计调用成功次数
	 */
	private Integer success;

	/**
	 * 累计调用失败次数
	 */
	private Integer failure;

	/**
	 * 平均调用时长
	 */
	private Integer elapsed;

	/**
	 * 平均并发
	 */
	private Integer concurrent;

	/**
	 * 最大调用时长
	 */
	private Integer maxElapsed;

	/**
	 * 最大并发
	 */
	private Integer maxConcurrent;

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

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public Integer getElapsed() {
		return elapsed;
	}

	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}

	public Integer getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(Integer concurrent) {
		this.concurrent = concurrent;
	}

	public Integer getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(Integer maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public Integer getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(Integer maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
