package com.yeepay.g3.core.soa.center.facade.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.dubbo.service.ConsumerService;
import com.yeepay.g3.core.soa.center.dubbo.service.OverrideService;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.dubbo.service.RouteService;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.ParseUtils;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.RouteRule;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.RouteUtils;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.facade.soa.center.utils.OverrideUtils;
import com.yeepay.g3.facade.soa.center.utils.Tool;

/**
 * Title: 消费者 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:06
 */
@Service
public class ConsumerFacadeImpl implements ConsumerFacade {

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private OverrideService overrideService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private RouteService routeService;

	@java.lang.Override
	public List<Consumer> findByService(String serviceName) {
		return consumerService.findByService(serviceName);
	}

	@java.lang.Override
	public Consumer findConsumer(Long id) {
		return consumerService.findConsumer(id);
	}

	@java.lang.Override
	public Consumer findConsumerDetail(Long id) {
		Consumer consumer = consumerService.findConsumer(id);
		List<Provider> providers = providerService.findByService(consumer.getService());
		List<Route> routes = routeService.findByService(consumer.getService());
		List<Override> overrides = overrideService.findByService(consumer.getService());
		List<Route> routed = new ArrayList<Route>();
		consumer.setProviders(RouteUtils.route(consumer.getService(), consumer.getAddress(), consumer.getParameters(), providers, overrides, routes, null, routed));
		consumer.setRoutes(routed);
		OverrideUtils.setConsumerOverrides(consumer, overrides);

		// 附加信息
		consumer.setForbid(isInBlackList(consumer));
		consumer.setMock(Tool.getConsumerMock(consumer));
		return consumer;
	}

	@java.lang.Override
	public List<Consumer> findAll() {
		return consumerService.findAll();
	}

	@java.lang.Override
	public Set<String> findAddresses() {
		return consumerService.findAddresses();
	}

	@java.lang.Override
	public Set<String> findAddressesByApplication(String application) {
		return consumerService.findAddressesByApplication(application);
	}

	@java.lang.Override
	public Set<String> findAddressesByService(String serviceName) {
		return consumerService.findAddressesByService(serviceName);
	}

	@java.lang.Override
	public List<Consumer> findByAddress(String consumerAddress) {
		List<Consumer> consumers = consumerService.findByAddress(consumerAddress);
		DataConvertUtils.sort(consumers, "service");
		return consumers;
	}

	@java.lang.Override
	public List<String> findServicesByAddress(String consumerAddress) {
		return consumerService.findServicesByAddress(consumerAddress);
	}

	@java.lang.Override
	public Set<String> findApplications() {
		return consumerService.findApplications();
	}

	@java.lang.Override
	public Set<String> findApplicationsByServiceName(String serviceName) {
		return consumerService.findApplicationsByServiceName(serviceName);
	}

	@java.lang.Override
	public List<Consumer> findByApplication(String application) {
		List<Consumer> consumers = consumerService.findByApplication(application);
		DataConvertUtils.sort(consumers, "service");
		return consumers;
	}

	@java.lang.Override
	public List<String> findServicesByApplication(String application) {
		return consumerService.findServicesByApplication(application);
	}

	@java.lang.Override
	public List<String> findServices() {
		return consumerService.findServices();
	}

	@java.lang.Override
	public List<Consumer> findWithOverrideByService(String serviceName) {
		if (StringUtils.isBlank(serviceName)) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		List<Consumer> consumers = consumerService.findByService(serviceName);
		List<Override> overrides = overrideService.findByService(serviceName);
		List<Provider> providers = providerService.findByService(serviceName);
		List<Route> routes = routeService.findByService(serviceName);
		return setConsumerOverrides(serviceName, consumers, overrides, providers, routes);
	}

	@java.lang.Override
	public List<Consumer> findWithOverrideByAddress(String address) {
		if (StringUtils.isBlank(address)) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		List<Consumer> consumers = consumerService.findByAddress(address);
		List<Override> overrides = overrideService.findByAddress(Tool.getIP(address));
		return setConsumerOverrides(null, consumers, overrides, null, null);
	}

	@java.lang.Override
	public List<Consumer> findWithOverrideByApplication(String application) {
		if (StringUtils.isBlank(application)) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		List<Consumer> consumers = consumerService.findByApplication(application);
		List<Override> overrides = overrideService.findByApplication(application);
		return setConsumerOverrides(null, consumers, overrides, null, null);
	}

	@java.lang.Override
	public List<Consumer> findAllWithOverride() {
		List<Consumer> consumers = consumerService.findAll();
		List<Override> overrides = overrideService.findAll();
		return setConsumerOverrides(null, consumers, overrides, null, null);
	}

	/**
	 * @param serviceName 服务名
	 * @param consumers   消费者
	 * @param overrides   动态配置
	 * @param providers   服务提供者
	 * @param routes      路由信息
	 * @return
	 */
	private List<Consumer> setConsumerOverrides(String serviceName, List<Consumer> consumers, List<Override> overrides, List<Provider> providers, List<Route> routes) {
		if (consumers != null && consumers.size() > 0) {
			for (Consumer consumer : consumers) {
				if (serviceName == null || serviceName.length() == 0) {
					providers = providerService.findByService(consumer.getService());
					routes = routeService.findByService(consumer.getService());
				}

				List<Route> routed = new ArrayList<Route>();
				consumer.setProviders(RouteUtils.route(consumer.getService(), consumer.getAddress(), consumer.getParameters(), providers, overrides, routes, null, routed));
				consumer.setRoutes(routed);
				OverrideUtils.setConsumerOverrides(consumer, overrides);

				// 附加信息
				consumer.setForbid(isInBlackList(consumer));
				consumer.setMock(Tool.getConsumerMock(consumer));
			}
		}
		return consumers;
	}

	/**
	 * 判断是否禁用
	 *
	 * @param consumer
	 * @return
	 */
	public boolean isInBlackList(Consumer consumer) {
		String service = consumer.getService();
		List<Route> routes = routeService.findForceRouteByService(service);
		if (routes == null || routes.size() == 0) {
			return false;
		}
		String ip = Tool.getIP(consumer.getAddress());
		for (Route route : routes) {
			try {
				if (!route.isEnabled()) {
					continue;
				}
				String filterRule = route.getFilterRule();
				if (filterRule == null || filterRule.length() == 0 || "false".equals(filterRule)) {
					Map<String, RouteRule.MatchPair> rule = RouteRule.parseRule(route.getMatchRule());
					RouteRule.MatchPair pair = rule.get("consumer.host");
					if (pair == null) {
						pair = rule.get("host");
					}
					if (pair != null) {
						if (pair.getMatches() != null && pair.getMatches().size() > 0) {
							for (String host : pair.getMatches()) {
								if (ParseUtils.isMatchGlobPattern(host, ip)) {
									return true;
								}
							}
						}
						if (pair.getUnmatches() != null && pair.getUnmatches().size() > 0) {
							boolean forbid = true;
							for (String host : pair.getUnmatches()) {
								if (ParseUtils.isMatchGlobPattern(host, ip)) {
									forbid = false;
								}
							}
							if (forbid) {
								return true;
							}
						}
					}
				}
			} catch (ParseException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return false;
	}

	public boolean shield(Long[] ids, String operator, String operatorAddress) {
		return mock(ids, "force:return null", operator, operatorAddress);
	}

	public boolean tolerant(Long[] ids, String operator, String operatorAddress) {
		return mock(ids, "fail:return null", operator, operatorAddress);
	}

	public boolean recover(Long[] ids, String operator, String operatorAddress) {
		return mock(ids, "", operator, operatorAddress);
	}

	private boolean mock(Long[] ids, String mock, String operator, String operatorAddress) {
		if (null == ids || ids.length == 0 || StringUtils.isBlank(operator)) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		List<Consumer> consumers = new ArrayList<Consumer>();
		for (Long id : ids) {
			Consumer c = consumerService.findConsumer(id);
			if (c != null) {
				consumers.add(c);

				// TODO 权限控制
//				if (!super.currentUser.hasServicePrivilege(w.getService())) {
//					throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", s);
//					return false;
//				}
			}
		}
		for (Consumer consumer : consumers) {
			String service = consumer.getService();
			String address = Tool.getIP(consumer.getAddress());
			List<Override> overrides = overrideService.findByServiceAndAddress(service, address);
			if (overrides != null && overrides.size() > 0) {
				for (Override override : overrides) {
					Map<String, String> map = com.alibaba.dubbo.common.utils.StringUtils.parseQueryString(override.getParams());
					if (mock == null || mock.length() == 0) {
						map.remove("mock");
					} else {
						map.put("mock", URL.encode(mock));
					}
					if (map.size() > 0) {
						override.setParams(com.alibaba.dubbo.common.utils.StringUtils.toQueryString(map));
						override.setEnabled(true);
						override.setOperator(operator);
						override.setOperatorAddress(operatorAddress);
						overrideService.updateOverride(override);
					} else {
						overrideService.deleteOverride(override.getId());
					}
				}
			} else if (mock != null && mock.length() > 0) {
				Override override = new Override();
				override.setService(service);
				override.setAddress(address);
				override.setParams("mock=" + URL.encode(mock));
				override.setEnabled(true);
				override.setOperator(operator);
				override.setOperatorAddress(operatorAddress);
				overrideService.saveOverride(override);
			}
		}
		return true;
	}

	public boolean allshield(String serviceName, String operator, String operatorAddress) {
		return allmock(serviceName, "force:return null", operator, operatorAddress);
	}

	public boolean alltolerant(String serviceName, String operator, String operatorAddress) {
		return allmock(serviceName, "fail:return null", operator, operatorAddress);
	}

	public boolean allrecover(String serviceName, String operator, String operatorAddress) {
		return allmock(serviceName, "", operator, operatorAddress);
	}

	private boolean allmock(String serviceName, String mock, String operator, String operatorAddress) {
		if (StringUtils.isBlank(serviceName)) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		// TODO 权限控制
//		if (!super.currentUser.hasServicePrivilege(w.getService())) {
//			throw SOAException.NO_PRIVILEGE_EXCEPTION
//					.newInstance("无权访问: {0}", s);
//			return false;
//		}

		List<Override> overrides = overrideService.findByService(serviceName);
		Override allOverride = null;
		if (overrides != null && overrides.size() > 0) {
			for (Override override : overrides) {
				if (override.isDefault()) {
					allOverride = override;
					break;
				}
			}
		}
		if (allOverride != null) {
			Map<String, String> map = com.alibaba.dubbo.common.utils.StringUtils.parseQueryString(allOverride.getParams());
			if (mock == null || mock.length() == 0) {
				map.remove("mock");
			} else {
				map.put("mock", URL.encode(mock));
			}
			if (map.size() > 0) {
				allOverride.setParams(com.alibaba.dubbo.common.utils.StringUtils.toQueryString(map));
				allOverride.setEnabled(true);
				allOverride.setOperator(operator);
				allOverride.setOperatorAddress(operatorAddress);
				overrideService.updateOverride(allOverride);
			} else {
				overrideService.deleteOverride(allOverride.getId());
			}
		} else if (mock != null && mock.length() > 0) {
			Override override = new Override();
			override.setService(serviceName);
			override.setParams("mock=" + URL.encode(mock));
			override.setEnabled(true);
			override.setOperator(operator);
			override.setOperatorAddress(operatorAddress);
			overrideService.saveOverride(override);
		}
		return true;
	}

	public boolean allow(Long[] ids, String operator) throws ParseException {
		return access(ids, true, false, operator);
	}

	public boolean forbid(Long[] ids, String operator) throws ParseException {
		return access(ids, false, false, operator);
	}

	public boolean onlyAllow(Long[] ids, String operator) throws ParseException {
		return access(ids, true, true, operator);
	}

	public boolean onlyForbid(Long[] ids, String operator) throws ParseException {
		return access(ids, false, true, operator);
	}

	private boolean access(Long[] ids, boolean allow, boolean only, String operator) throws ParseException {
		if (null == ids || ids.length == 0 || StringUtils.isBlank(operator)) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		List<Consumer> consumers = new ArrayList<Consumer>();
		for (Long id : ids) {
			Consumer c = consumerService.findConsumer(id);
			if (c != null) {
				consumers.add(c);

				// TODO 权限控制
//				if (!super.currentUser.hasServicePrivilege(w.getService())) {
//					throw SOAException.NO_PRIVILEGE_EXCEPTION
//							.newInstance("无权访问: {0}", s);
//					return false;
//				}
			}
		}

		Map<String, Set<String>> serviceAddresses = new HashMap<String, Set<String>>();
		for (Consumer consumer : consumers) {
			String service = consumer.getService();
			String address = Tool.getIP(consumer.getAddress());
			Set<String> addresses = serviceAddresses.get(service);
			if (addresses == null) {
				addresses = new HashSet<String>();
				serviceAddresses.put(service, addresses);
			}
			addresses.add(address);
		}
		for (Map.Entry<String, Set<String>> entry : serviceAddresses.entrySet()) {
			String service = entry.getKey();
			boolean isFirst = false;
			List<Route> routes = routeService.findForceRouteByService(service);
			Route route = null;
			if (routes == null || routes.size() == 0) {
				isFirst = true;
				route = new Route();
				route.setService(service);
				route.setForce(true);
				route.setName(service + " blackwhitelist");
				route.setFilterRule("false");
				route.setEnabled(true);
			} else {
				route = routes.get(0);
			}
			Map<String, RouteRule.MatchPair> when = null;
			RouteRule.MatchPair matchPair = null;
			if (isFirst) {
				when = new HashMap<String, RouteRule.MatchPair>();
				matchPair = new RouteRule.MatchPair(new HashSet<String>(), new HashSet<String>());
				when.put("consumer.host", matchPair);
			} else {
				when = RouteRule.parseRule(route.getMatchRule());
				matchPair = when.get("consumer.host");
			}
			if (only) {
				matchPair.getUnmatches().clear();
				matchPair.getMatches().clear();
				if (allow) {
					matchPair.getUnmatches().addAll(entry.getValue());
				} else {
					matchPair.getMatches().addAll(entry.getValue());
				}
			} else {
				for (String consumerAddress : entry.getValue()) {
					if (matchPair.getUnmatches().size() > 0) { // 白名单优先
						matchPair.getMatches().remove(consumerAddress); // 去掉黑名单中相同数据
						if (allow) { // 如果允许访问
							matchPair.getUnmatches().add(consumerAddress); // 加入白名单
						} else { // 如果禁止访问
							matchPair.getUnmatches().remove(consumerAddress); // 从白名单中去除
						}
					} else { // 黑名单生效
						if (allow) { // 如果允许访问
							matchPair.getMatches().remove(consumerAddress); // 从黑名单中去除
						} else { // 如果禁止访问
							matchPair.getMatches().add(consumerAddress); // 加入黑名单
						}
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			RouteRule.contidionToString(sb, when);
			route.setMatchRule(sb.toString());
			route.setUsername(operator);
			if (matchPair.getMatches().size() > 0 || matchPair.getUnmatches().size() > 0) {
				if (isFirst) {
					routeService.createRoute(route);
				} else {
					routeService.updateRoute(route);
				}
			} else if (!isFirst) {
				routeService.deleteRoute(route.getId());
			}
		}
		return true;
	}

}
