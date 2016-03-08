package com.yeepay.g3.core.soa.center.dubbo.service;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;

import java.util.List;

/**
 * RouteService
 *
 * @author william.liangf
 */
public interface RouteService {

	void createRoute(Route route);

	void updateRoute(Route route);

	void deleteRoute(Long id);

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

}
