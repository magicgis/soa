package com.yeepay.g3.facade.soa.center.dubbo.domain;

import java.io.Serializable;

/**
 * <p>Title: 依赖关系</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Dependency implements Serializable {

	private static final long serialVersionUID = 8526869025719540547L;

	private String providerApplication;

	private String consumerApplication;

	public String getProviderApplication() {
		return providerApplication;
	}

	public void setProviderApplication(String providerApplication) {
		this.providerApplication = providerApplication;
	}

	public String getConsumerApplication() {
		return consumerApplication;
	}

	public void setConsumerApplication(String consumerApplication) {
		this.consumerApplication = consumerApplication;
	}

}
