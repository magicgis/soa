package com.yeepay.g3.core.soa.center.dubbo.service;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Owner;

import java.util.List;

public interface OwnerService {

	List<String> findAllServiceNames();

	List<String> findServiceNamesByUsername(String username);

	List<String> findUsernamesByServiceName(String serviceName);

	List<Owner> findByService(String serviceName);

	List<Owner> findAll();

	Owner findById(Long id);

	void saveOwner(Owner owner);

	void deleteOwner(Owner owner);

}
