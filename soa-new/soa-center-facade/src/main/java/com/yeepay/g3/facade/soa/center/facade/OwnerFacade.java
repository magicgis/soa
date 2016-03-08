package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;

import java.util.List;

/**
 * Title: 负责人 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:34
 */
public interface OwnerFacade {

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
