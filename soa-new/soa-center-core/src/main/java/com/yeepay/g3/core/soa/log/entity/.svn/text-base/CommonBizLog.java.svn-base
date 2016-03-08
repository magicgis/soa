/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.log.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.utils.persistence.Entity;

/**
 * @author：wang.bao
 * @since：2014年11月5日 下午4:35:16
 * @version:
 */
public class CommonBizLog implements Entity<Long> {
	private static final long serialVersionUID = -4618578545161570089L;

	private Long id;

	/**
	 * 日志名
	 */
	private String loggerName;

	/**
	 * 应用名
	 */
	private String application;

	/**
	 * 机器
	 */
	private String host;

	/**
	 * 线程名
	 */
	private String threadName;

	/**
	 * guid
	 */
	private String guid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 日志内
	 */
	private String logContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
