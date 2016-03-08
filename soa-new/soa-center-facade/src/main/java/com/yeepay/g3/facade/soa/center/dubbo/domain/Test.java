package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: Route</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Test extends Entity {

	private static final long serialVersionUID = 872527738197173003L;

	private String name;

	private String service;

	private String method;

	private String parameters;

	private boolean exception;

	private String result;

	private String username;

	private boolean autoRun;

	public Test() {
	}

	public Test(Long id) {
		super(id);
	}

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

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

	public boolean isAutoRun() {
		return autoRun;
	}

	public void setAutoRun(boolean autoRun) {
		this.autoRun = autoRun;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
