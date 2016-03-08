package com.yeepay.g3.facade.soa.center.facade;

import java.util.List;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * Title: 动态配置 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:34
 */
public interface OverrideFacade {

	void saveOverride(com.yeepay.g3.facade.soa.center.dubbo.domain.Override override);

	void updateOverride(Override override);

	void deleteOverride(Long id);

	void deleteOverride(Long[] ids);

	void enableOverride(Long id);

	void disableOverride(Long id);

	List<Override> findByService(String service);

	List<Override> findByAddress(String address);

	List<Override> findByServiceAndAddress(String service, String address);

	List<Override> findByApplication(String application);

	List<Override> findByServiceAndApplication(String service, String application);

	List<Override> findAll();

	PageList findAll(int pageNo, int pageSize);

	Override findById(Long id);

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
