package com.yeepay.g3.facade.soa.center.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Title: 监控数据项<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 17:54
 */
public class MonitorDataItemDTO implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	/**
	 * 调用成功次数
	 */
	protected int success;

	/**
	 * 调用失败次数
	 */
	protected int failure;

	/**
	 * 调用时长
	 */
	protected int elapsed;

	/**
	 * 并发
	 */
	protected int concurrent;

	/**
	 * 最大调用时长
	 */
	protected int maxElapsed;

	/**
	 * 最大并发
	 */
	protected int maxConcurrent;

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public int getElapsed() {
		return elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public int getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(int concurrent) {
		this.concurrent = concurrent;
	}

	public int getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(int maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public int getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
