package com.yeepay.g3.facade.soa.center.dto;

import com.google.common.collect.Maps;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
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
public class MonitorChartDataDTOWarpper implements Serializable {

	private static final long serialVersionUID = 8363450384964204871L;

	/**
	 * type -> data
	 */
	private Map<String, MonitorChartDataDTO> data = Maps.newHashMap();

	public Map<String, MonitorChartDataDTO> getData() {
		return data;
	}

	public void setData(Map<String, MonitorChartDataDTO> data) {
		this.data = data;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
