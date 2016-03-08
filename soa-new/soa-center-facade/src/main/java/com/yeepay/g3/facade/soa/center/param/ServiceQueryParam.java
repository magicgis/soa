package com.yeepay.g3.facade.soa.center.param;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yeepay.g3.facade.soa.center.enums.QueryTypeEnum;

/**
 * 服务查询传输对象
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:22:21
 * @version:
 */
public class ServiceQueryParam implements Serializable {
	private static final long serialVersionUID = -4743596478192277023L;

	private QueryTypeEnum queryType;

	private String queryInput;

	private Integer pageNo;

	private Integer pageSize;

	private Map<String, Object> extParam;

	public QueryTypeEnum getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryTypeEnum queryType) {
		this.queryType = queryType;
	}

	public String getQueryInput() {
		return queryInput;
	}

	public void setQueryInput(String queryInput) {
		this.queryInput = queryInput;
	}

	public Integer getPageNo() {
		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		if (pageSize == null || pageSize < 1) {
			pageSize = 20;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getExtParam() {
		if (extParam == null) {
			extParam = new HashMap<String, Object>();
		}
		return extParam;
	}

	public void setExtParam(Map<String, Object> extParam) {
		this.extParam = extParam;
	}

	public void addExtParam(String key, Object value) {
		if (extParam == null) {
			extParam = new HashMap<String, Object>();
		}
		this.extParam.put(key, value);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
