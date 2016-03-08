package com.yeepay.g3.facade.soa.center.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yeepay.g3.facade.soa.center.utils.JsonDateSerializer;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * Title: 监控数据统计结果<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 17:57
 */
public class MonitorChartDataItemDTO implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	/**
	 * 横坐标轴
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date label;

	/**
	 * 吞吐量(QPS)
	 */
	private Integer qps;

	/**
	 * 平均响应时间(ms)
	 */
	private Integer art;

	public Date getLabel() {
		return label;
	}

	public void setLabel(Date label) {
		this.label = label;
	}

	public Integer getQps() {
		return qps;
	}

	public void setQps(Integer qps) {
		this.qps = qps;
	}

	public Integer getArt() {
		return art;
	}

	public void setArt(Integer art) {
		this.art = art;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
