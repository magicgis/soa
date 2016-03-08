package com.yeepay.g3.facade.soa.center.dubbo.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Title: 记录错误信息以及记录所有的人为的操作</p>
 * <p>Description: 描述</p>
 * <p>Copyright: Copyright (c)2011</p>
 * <p>Company: 易宝支付(YeePay)</p>
 *
 * @author baitao.ji
 * @version 0.1, 14-6-11 18:24
 */
public class Operation extends Entity {

	private static final long serialVersionUID = 8220325876753890396L;

	public static final String PROVIDER_TYPE = "Provider";

	public static final String CONSUMER_TYPE = "Consumer";

	public static final String ROUTE_TYPE = "Route";

	public static final String WEIGHT_TYPE = "Weight";

	public static final String CLUSTER_TYPE = "Cluster";

	public static final String DOCUMENT_TYPE = "Document";

	public static final String LOADBALANCE_TYPE = "LoadBalance";

	public static final String TEST_TYPE = "Test";

	public static final String MOCK_TYPE = "Mock";

	public static final String AGREEMENT_TYPE = "Agreement";

	public static final String APPROVAL_TYPE = "Approval";

	public static final String APPROVAL_REQUISITION_TYPE = "ApprovalRequisition";

	public static final String USER_TYPE = "User";

	public static final String FEATURE_TYPE = "Feature";

	public static final String LAYER_TYPE = "Layer";

	public static final String CONFIG_TYPE = "Config";

	public static final List<String> TYPES =
			Collections.unmodifiableList(Arrays.asList(new String[]{PROVIDER_TYPE, CONSUMER_TYPE, ROUTE_TYPE, WEIGHT_TYPE,
					CLUSTER_TYPE, DOCUMENT_TYPE, LOADBALANCE_TYPE, TEST_TYPE,
					AGREEMENT_TYPE, USER_TYPE, FEATURE_TYPE, CONFIG_TYPE}));

	private String username;     //操作者

	private String dataType;     //数据类型，如route,cluster

	private String operateType;    //操作类型，如删除、修改 、创建

	private String data;       //记录数据信息

	public Operation() {
	}

	public Operation(Long id) {
		super(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		if (data != null && data.length() > 1000) {
			data = data.substring(0, 1000);
		}
		this.data = data;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
}
