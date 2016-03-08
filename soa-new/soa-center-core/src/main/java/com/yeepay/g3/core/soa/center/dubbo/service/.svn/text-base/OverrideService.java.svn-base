package com.yeepay.g3.core.soa.center.dubbo.service;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;

import java.util.List;

/**
 * @author tony.chenl
 */
public interface OverrideService {

	void saveOverride(Override override);

	void updateOverride(Override override);

	void deleteOverride(Long id);

	void enableOverride(Long id);

	void disableOverride(Long id);

	List<Override> findByService(String service);

	List<Override> findByAddress(String address);

	List<Override> findByServiceAndAddress(String service, String address);

	List<Override> findByApplication(String application);

	List<Override> findByServiceAndApplication(String service, String application);

	List<Override> findAll();

	Override findById(Long id);

}
