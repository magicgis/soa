/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.facade.soa.center.exceptions;

import com.yeepay.g3.common.exception.CoreBizException;

/**
 * Title: 微信异常类<br/>
 * Description: <br/>
 * 0xxx 系统内部异常<br/>
 * 1xxx 系统外部异常<br/>
 * 2xxx 权限相关<br/>
 * 3xxx 服务相关<br/>
 * 4xxx 服务提供者相关<br/>
 * 5xxx 服务消费者相关<br/>
 * 6xxx 路由规则相关<br/>
 * 7xxx 访问控制相关<br/>
 * 8xxx 动态配置相关<br/>
 * 9xxx 负载均衡相关<br/>
 * 10xxx 权重调节相关<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-3-24 9:22
 */
public final class SOAException extends CoreBizException {

	private static final long serialVersionUID = 1L;

	/**
	 * 未知异常
	 */
	public static final SOAException UNKNOWN_EXCEPTION = new SOAException("0000");

	/**
	 * 加载统一配置报错
	 */
	public static final SOAException LOAD_CONFIG_EXCEPTION = new SOAException("0001");

	/**
	 * 参数异常
	 */
	public static final SOAException ILLEGAL_ARGS_EXCEPTION = new SOAException("1001");

	/**
	 * 无权访问
	 */
	public static final SOAException NO_PRIVILEGE_EXCEPTION = new SOAException("2001");

	/**
	 * 服务不存在
	 */
	public static final SOAException SERVICE_NOT_EXISTS_EXCEPTION = new SOAException("4000");

	/**
	 * 非法IP
	 */
	public static final SOAException ILLEGAL_IP_EXCEPTION = new SOAException("3001");

	/**
	 * 非法端口
	 */
	public static final SOAException ILLEGAL_PORT_EXCEPTION = new SOAException("3002");

	/**
	 * 非法IP或端口
	 */
	public static final SOAException ILLEGAL_IP_OR_PORT_EXCEPTION = new SOAException("3003");

	/**
	 * 服务提供者不存在
	 */
	public static final SOAException PROVIDER_NOT_EXISTS_EXCEPTION = new SOAException("4000");

	/**
	 * 不能删除静态服务提供者
	 */
	public static final SOAException CANNOT_DELETE_STATIC_PROVIDER_EXCEPTION = new SOAException("4001");

	/**
	 * 服务消费者不存在
	 */
	public static final SOAException CONSUMER_NOT_EXISTS_EXCEPTION = new SOAException("5000");

	/**
	 * 路由规则不存在
	 */
	public static final SOAException ROUTERULE_NOT_EXISTS_EXCEPTION = new SOAException("6000");

	/**
	 * 匹配规则个数超限
	 */
	public static final SOAException MATCHRULE_TOO_MANY_EXCEPTION = new SOAException("6001");

	/**
	 * 过滤规则个数超限
	 */
	public static final SOAException FILTERRULE_TOO_MANY_EXCEPTION = new SOAException("6002");

	/**
	 * 过滤规则不能为空
	 */
	public static final SOAException FILTERRULE_CANOT_EMPTY_EXCEPTION = new SOAException("6003");

	// 动态配置

	/**
	 * 动态配置信息不能为空
	 */
	public static final SOAException OVERRIDE_VALUE_IS_NULL_EXCEPTION = new SOAException("8001");

	/**
	 * 动态配置信息的key、value个数不匹配
	 */
	public static final SOAException OVERRIDE_LENGTH_NOT_EQUALS_EXCEPTION = new SOAException("8002");

	/**
	 * 服务降级信息的key、value个数不匹配
	 */
	public static final SOAException MOCK_LENGTH_NOT_EQUALS_EXCEPTION = new SOAException("8003");

	private SOAException(String defineCode) {
		super(defineCode);

	}

	public SOAException newInstance(String message, Object... args) {
		SOAException e = new SOAException(this.defineCode);
		e.setMessage(message, args);
		return e;
	}

}
