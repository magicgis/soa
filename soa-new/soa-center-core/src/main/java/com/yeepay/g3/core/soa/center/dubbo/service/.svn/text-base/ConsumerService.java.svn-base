package com.yeepay.g3.core.soa.center.dubbo.service;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;

import java.util.List;
import java.util.Set;

/**
 * 消费者数据访问对象
 *
 * @author william.liangf
 */
public interface ConsumerService {

	List<Consumer> findByService(String serviceName);

	Consumer findConsumer(Long id);

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

	void updateConsumer(Consumer consumer);
}