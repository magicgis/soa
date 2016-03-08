package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

/**
 * Title: 消费者 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:06
 */
public interface ConsumerFacade {

	List<Consumer> findByService(String serviceName);

	Consumer findConsumer(Long id);

	Consumer findConsumerDetail(Long id);

	List<Consumer> findAll();

	/**
	 * 查询所有的消费者地址
	 */
	Set<String> findAddresses();

	Set<String> findAddressesByApplication(String application);

	Set<String> findAddressesByService(String serviceName);

	List<Consumer> findByAddress(String consumerAddress);

	List<String> findServicesByAddress(String consumerAddress);

	Set<String> findApplications();

	Set<String> findApplicationsByServiceName(String serviceName);

	List<Consumer> findByApplication(String application);

	List<String> findServicesByApplication(String application);

	List<String> findServices();

	/**
	 * 查询消费者，带配置信息
	 *
	 * @param serviceName 服务名
	 * @return
	 */
	List<Consumer> findWithOverrideByService(String serviceName);

	/**
	 * 查询消费者，带配置信息
	 *
	 * @param address 机器
	 * @return
	 */
	List<Consumer> findWithOverrideByAddress(String address);

	/**
	 * 查询消费者，带配置信息
	 *
	 * @param application 应用
	 * @return
	 */
	List<Consumer> findWithOverrideByApplication(String application);

	/**
	 * 查询消费者，带配置信息
	 *
	 * @return
	 */
	List<Consumer> findAllWithOverride();

	/**
	 * 屏蔽 force:return null
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	boolean shield(Long[] ids, String operator, String operatorAddress);

	/**
	 * 容错 fail:return null
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	boolean tolerant(Long[] ids, String operator, String operatorAddress);

	/**
	 * 恢复
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	boolean recover(Long[] ids, String operator, String operatorAddress);

	/**
	 * 批量屏蔽 force:return null
	 *
	 * @param serviceName     服务名
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	boolean allshield(String serviceName, String operator, String operatorAddress);

	/**
	 * 批量容错 fail:return null
	 *
	 * @param serviceName     服务名
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	boolean alltolerant(String serviceName, String operator, String operatorAddress);

	/**
	 * 批量恢复
	 *
	 * @param serviceName     服务名
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	boolean allrecover(String serviceName, String operator, String operatorAddress);

	/**
	 * 允许
	 *
	 * @param ids
	 * @param operator 操作员
	 * @return
	 */
	boolean allow(Long[] ids, String operator) throws ParseException;

	/**
	 * 禁止
	 *
	 * @param ids
	 * @param operator 操作员
	 * @return
	 */
	boolean forbid(Long[] ids, String operator) throws ParseException;

	/**
	 * 只允许
	 *
	 * @param ids
	 * @param operator 操作员
	 * @return
	 */
	boolean onlyAllow(Long[] ids, String operator) throws ParseException;

	/**
	 * 只禁止
	 *
	 * @param ids
	 * @param operator 操作员
	 * @return
	 */
	boolean onlyForbid(Long[] ids, String operator) throws ParseException;

	/**
	 * 判断是否禁用
	 *
	 * @param consumer
	 * @return
	 */
	public boolean isInBlackList(Consumer consumer);
}
