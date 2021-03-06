package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 服务上线请求</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:54
 */
public class ApprovalRequisition extends Entity {

	private static final long serialVersionUID = -8778092807313048367L;

	/**
	 * 申请操作：新增（C），修改（U），删除（D）
	 */
	private char operation;

	private Long approvalId;

	/**
	 * 服务名称
	 */
	private String service;

	/**
	 * 服务版本
	 * <p/>
	 * 可以使用通配符， *，1.1.*
	 * 可以包含多个 1.1.3,1.1.5,2.*
	 */
	private String version;

	private boolean forProvider;

	/**
	 * 服务所在机器
	 * <p/>
	 * 可以使用通配符 172.3.8.*
	 * 可以有多个 172.1.9.8,172.1.9.123,172.3.3.*
	 */
	private String machineList;

	private String username;

	public char getOperation() {
		return operation;
	}

	public void setOperation(char operation) {
		this.operation = operation;
	}

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isForProvider() {
		return forProvider;
	}

	public void setForProvider(boolean forProvider) {
		this.forProvider = forProvider;
	}

	public String getMachineList() {
		return machineList;
	}

	public void setMachineList(String machineList) {
		this.machineList = machineList;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
