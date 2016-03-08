package com.yeepay.g3.facade.soa.center.dubbo.domain;

/**
 * <p>Title: 服务变更信息对象</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Change extends Entity {

	private static final long serialVersionUID = 15528419903956898L;

	/**
	 * 服务提供变更
	 */
	public static final String PROVIDER_TYPE = "P";

	/**
	 * 服务消费者变更
	 */
	public static final String CONSUMER_TYPE = "N";

	/**
	 * 路由变更
	 */
	public static final String ROUTE_TYPE = "R";

	/**
	 * 权重变更
	 */
	public static final String WEIGHT_TYPE = "W";

	/**
	 * 负载均衡变更
	 */
	public static final String LOADBALANCE_TYPE = "L";

	/**
	 * 分组变更
	 */
	public static final String CLUSTER_TYPE = "G";

	/**
	 * 用户变更
	 */
	public static final String USER_TYPE = "U";

	/**
	 * 系统配置变更
	 */
	public static final String CONFIG_TYPE = "C";

	/**
	 * 系统功能变更
	 */
	public static final String FEATURE_TYPE = "F";

	/**
	 * 系统功能变更
	 */
	public static final String LAYER_TYPE = "Y";

	/**
	 * 服务测试变更
	 */
	public static final String TEST_TYPE = "T";

	/**
	 * 服务测试变更
	 */
	public static final String MOCK_TYPE = "M";

	/**
	 * 服务访问控制变更
	 */
	public static final String ACCESS_TYPE = "A";

	/**
	 * 参数覆盖变更
	 */
	public static final String OVERRIDE_TYPE = "O";

	/**
	 * 变更类型
	 */
	private String type;

	/**
	 * 服务名称
	 */
	private String service;

	/**
	 * 变更序号
	 */
	private long sequence;

	/**
	 * 变更内容
	 */
	private String data;

	public Change() {
	}

	public Change(Long id) {
		super(id);
	}

	public Change(String type, String serviceName) {
		this.type = type;
		this.service = serviceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@Deprecated
	/**
	 * 用change的id作为sequence
	 */
	public long getSequence() {
		return sequence;
	}

	@Deprecated
	/**
	 * 用change的id作为sequence
	 */
	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
