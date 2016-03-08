package com.yeepay.g3.core.soa.center.dubbo.service;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;

import java.util.List;

/**
 * ProviderService
 */
public interface ProviderService {

	void create(Provider provider);

	void enableProvider(Long id);

	void disableProvider(Long id);

	void doublingProvider(Long id);

	void halvingProvider(Long id);

	void deleteStaticProvider(Long id);

	void updateProvider(Provider provider);

	Provider findProvider(Long id);

	List<String> findServices();

	List<String> findAddresses();

	List<String> findAddressesByApplication(String application);

	List<String> findAddressesByService(String serviceName);

	List<String> findApplicationsByServiceName(String serviceName);

	List<Provider> findByService(String serviceName);

	List<Provider> findAll();

	List<Provider> findByAddress(String providerAddress);

	List<String> findServicesByAddress(String providerAddress);

	List<String> findApplications();

	List<Provider> findByApplication(String application);

	List<String> findServicesByApplication(String application);

	List<String> findMethodsByService(String serviceName);

	Provider findByServiceAndAddress(String service, String address);

}