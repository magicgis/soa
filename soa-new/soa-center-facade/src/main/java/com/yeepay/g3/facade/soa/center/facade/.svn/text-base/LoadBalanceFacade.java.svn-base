package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.LoadBalance;

import java.io.IOException;
import java.util.List;

/**
 * Title: 负载均衡 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 16:36
 */
public interface LoadBalanceFacade {

	List<LoadBalance> findByService(String service);

	List<LoadBalance> findAll();

	/**
	 * 获取负载均衡信息
	 *
	 * @param id 负载均衡编号
	 * @return
	 */
	LoadBalance findOne(Long id);

	/**
	 * 新建负载均衡调节规则
	 *
	 * @param loadBalance 负载均衡
	 * @param operator    操作员
	 */
	void create(LoadBalance loadBalance, String operator) throws IOException;

	/**
	 * 修改负载均衡调节规则
	 *
	 * @param loadBalance 负载均衡
	 * @param operator    操作员
	 */
	void edit(LoadBalance loadBalance, String operator);

	/**
	 * 删除负载均衡调节规则
	 *
	 * @param ids      编号
	 * @param operator 操作员
	 */
	void delete(Long[] ids, String operator);

}
