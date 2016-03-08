package com.yeepay.g3.facade.soa.center.dubbo.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: 实体基类</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:49
 */
public abstract class Entity implements Serializable {

	private static final long serialVersionUID = -3031128781434583143L;

	private List<Long> ids;

	private Long id;

	private Date created;

	private Date modified;

	private Date now;

	private String operator;

	private String operatorAddress;

	private boolean miss;

	public Entity() {
	}

	public Entity(Long id) {
		this.id = id;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		if (operator != null && operator.length() > 200) {
			operator = operator.substring(0, 200);
		}
		this.operator = operator;
	}

	public String getOperatorAddress() {
		return operatorAddress;
	}

	public void setOperatorAddress(String operatorAddress) {
		this.operatorAddress = operatorAddress;
	}

	public boolean isMiss() {
		return miss;
	}

	public void setMiss(boolean miss) {
		this.miss = miss;
	}

}
