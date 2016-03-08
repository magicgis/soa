package com.yeepay.g3.core.soa.center.entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
public class MonitorChartData implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	/**
	 * 服务
	 */
	private String service;

	/**
	 * 方法
	 */
	private String method;

	private String type;

	private String time;

	/**
	 * 调用成功次数
	 */
	private int success;

	/**
	 * 吞吐量
	 */
	private int qps;

	/**
	 * 调用时长
	 */
	private int elapsed;

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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getQps() {
		return qps;
	}

	public void setQps(int qps) {
		this.qps = qps;
	}

	public int getElapsed() {
		return elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
