package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Weight;

import java.io.IOException;
import java.util.List;

/**
 * Title: 权重调节 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 10:45
 */
public interface WeightFacade {

	List<Weight> findByService(String service);

	List<Weight> findByAddress(String address);

	List<Weight> findAll();

	/**
	 * 获取权重信息
	 *
	 * @param id 权重编号
	 * @return
	 */
	Weight findOne(Long id);

	/**
	 * 新建权重调节规则
	 *
	 * @param weight   权重
	 * @param operator 操作员
	 */
	void create(Weight weight, String operator) throws IOException;

	/**
	 * 修改权重调节规则
	 *
	 * @param weight   权重
	 * @param operator 操作员
	 */
	void edit(Weight weight, String operator);

	/**
	 * 删除权重调节规则
	 *
	 * @param ids      编号
	 * @param operator 操作员
	 */
	void delete(Long[] ids, String operator);

}
