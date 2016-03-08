package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;

import java.util.List;
import java.util.Set;

/**
 * Title: 服务提供者 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:35
 */
public interface ProviderFacade {

	void create(Provider provider);

	void enableProvider(Long id);

	void disableProvider(Long id);

	void doublingProvider(Long id);

	void halvingProvider(Long id);

	void deleteStaticProvider(Long id);

	void deleteStaticProvider(Long[] ids);

	void updateProvider(Provider provider);

	Provider findProvider(Long id);

	Provider findProviderDetail(Long id);

	/**
	 * 查询服务
	 *
	 * @return
	 */
	List<String> findServices();

	/**
	 * 查询服务（根据服务名称进行排序）
	 *
	 * @return
	 */
	List<String> findServicesSorted();

	List<String> findServicesByApplication(String application);

	List<String> findServicesByAddress(String providerAddress);

	// 查询机器
	Set<String> findAddresses();

	Set<String> findAddressesByApplication(String application);

	Set<String> findAddressesByService(String serviceName);

	// 查询应用
	Set<String> findApplications();

	Set<String> findApplicationsByServiceName(String serviceName);

	// 查询方法
	Set<String> findMethodsByService(String serviceName);

	// 查询服务生产者
	List<Provider> findByService(String serviceName);

	/**
	 * 查询所有服务提供者
	 *
	 * @return
	 */
	List<Provider> findAll();

	/**
	 * 查询所有服务提供者（根据服务名称进行排序）
	 *
	 * @return
	 */
	List<Provider> findAllSorted();

	List<Provider> findByAddress(String providerAddress);

	List<Provider> findByApplication(String application);

	Provider findByServiceAndAddress(String service, String address);

	boolean isProviderEnabled(Provider provider);

	int getProviderWeight(Provider provider);

	/**
	 * 倍权
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	void doubling(Long[] ids, String operator, String operatorAddress);

	/**
	 * 半权
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	void halving(Long[] ids, String operator, String operatorAddress);

	/**
	 * 禁用
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	void forbid(Long[] ids, String operator, String operatorAddress);

	/**
	 * 启用
	 *
	 * @param ids
	 * @param operator        操作员
	 * @param operatorAddress 操作ip
	 * @return
	 */
	void allow(Long[] ids, String operator, String operatorAddress);

}
