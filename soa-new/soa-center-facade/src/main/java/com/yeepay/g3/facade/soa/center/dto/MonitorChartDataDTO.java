package com.yeepay.g3.facade.soa.center.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Title: 监控数据统计结果<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 17:57
 */
public class MonitorChartDataDTO implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	private Integer maxQPS;

	private Integer minQPS;

	private Double avgQPS;

	private Integer sumQPS;

	private Integer maxArt;

	private Double avgArt;

	private List<MonitorChartDataItemDTO> data = Lists.newArrayList();

	public Integer getMaxQPS() {
		return maxQPS;
	}

	public void setMaxQPS(Integer maxQPS) {
		this.maxQPS = maxQPS;
	}

	public Integer getMinQPS() {
		return minQPS;
	}

	public void setMinQPS(Integer minQPS) {
		this.minQPS = minQPS;
	}

	public Double getAvgQPS() {
		return avgQPS;
	}

	public void setAvgQPS(Double avgQPS) {
		this.avgQPS = avgQPS;
	}

	public Integer getSumQPS() {
		return sumQPS;
	}

	public void setSumQPS(Integer sumQPS) {
		this.sumQPS = sumQPS;
	}

	public Integer getMaxArt() {
		return maxArt;
	}

	public void setMaxArt(Integer maxArt) {
		this.maxArt = maxArt;
	}

	public Double getAvgArt() {
		return avgArt;
	}

	public void setAvgArt(Double avgArt) {
		this.avgArt = avgArt;
	}

	public List<MonitorChartDataItemDTO> getData() {
		return data;
	}

	public void setData(List<MonitorChartDataItemDTO> data) {
		this.data = data;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
