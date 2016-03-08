package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Title: 路由 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:35
 */
public interface RouteFacade {

	void createRoute(Route route);

	void createRoute(Route route, Map params);

	void updateRoute(Route route);

	void updateRoute(Route route, Map params);

	void deleteRoute(Long id);

	void deleteRoute(Long[] ids);

	void enableRoute(Long id);

	void disableRoute(Long id);

	Route findRoute(Long id);

	List<Route> findAll();

	List<Route> findByService(String serviceName);

	List<Route> findByAddress(String address);

	List<Route> findByServiceAndAddress(String service, String address);

	List<Route> findForceRouteByService(String service);

	List<Route> findForceRouteByAddress(String address);

	List<Route> findForceRouteByServiceAndAddress(String service, String address);

	List<Route> findAllForceRoute();

	boolean isInBlackList(Consumer consumer);

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

	Map<String, String> routeRuleParse(Long id) throws ParseException;

}
