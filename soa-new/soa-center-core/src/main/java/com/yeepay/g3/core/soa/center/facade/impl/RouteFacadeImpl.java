package com.yeepay.g3.core.soa.center.facade.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Maps;
import com.yeepay.g3.core.soa.center.dubbo.service.RouteService;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.RouteRule;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.facade.soa.center.facade.RouteFacade;

/**
 * Title: 路由 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 19:06
 */
@Service
public class RouteFacadeImpl implements RouteFacade {

	private static final int MAX_RULE_LENGTH = 1000;

	static String[][] when_names = {
			{"method", "method", "unmethod"},
			{"consumer.application", "consumerApplication", "unconsumerApplication"},
			{"consumer.cluster", "consumerCluster", "unconsumerCluster"},
			{"consumer.host", "consumerHost", "unconsumerHost"},
			{"consumer.version", "consumerVersion", "unconsumerVersion"},
			{"consumer.group", "consumerGroup", "unconsumerGroup"},
	};

	static String[][] then_names = {
			{"provider.application", "providerApplication", "unproviderApplication"},
			{"provider.cluster", "providerCluster", "unproviderCluster"}, // 要校验Cluster是否存在
			{"provider.host", "providerHost", "unproviderHost"},
			{"provider.protocol", "providerProtocol", "unproviderProtocol"},
			{"provider.port", "providerPort", "unproviderPort"},
			{"provider.version", "providerVersion", "unproviderVersion"},
			{"provider.group", "providerGroup", "unproviderGroup"}
	};

	@Autowired
	private RouteService routeService;

	@Override
	public void createRoute(Route route) {
		routeService.createRoute(route);
	}

	@Override
	public void createRoute(Route route, Map params) {
		if (StringUtils.isNotEmpty(route.getService())
				&& StringUtils.isNotEmpty(route.getName())) {
			checkService(route.getService());

			Map<String, String> when_name2valueList = Maps.newHashMap();
			Map<String, String> notWhen_name2valueList = Maps.newHashMap();
			for (String[] names : when_names) {
				when_name2valueList.put(names[0], extractToString(params.get(names[1])));
				notWhen_name2valueList.put(names[0], extractToString(params.get(names[2]))); // value不为null的情况，这里处理，后面会保证
			}

			Map<String, String> then_name2valueList = Maps.newHashMap();
			Map<String, String> notThen_name2valueList = Maps.newHashMap();
			for (String[] names : then_names) {
				then_name2valueList.put(names[0], extractToString(params.get(names[1])));
				notThen_name2valueList.put(names[0], extractToString(params.get(names[2])));
			}

			RouteRule routeRule = RouteRule.createFromNameAndValueListString(
					when_name2valueList, notWhen_name2valueList,
					then_name2valueList, notThen_name2valueList);

			if (routeRule.getThenCondition().isEmpty()) {
				throw SOAException.FILTERRULE_CANOT_EMPTY_EXCEPTION
						.newInstance("过滤规则不能为空");
			}

			String matchRule = routeRule.getWhenConditionString();
			String filterRule = routeRule.getThenConditionString();

			//限制表达式的长度
			if (matchRule.length() > MAX_RULE_LENGTH) {
				throw SOAException.MATCHRULE_TOO_MANY_EXCEPTION
						.newInstance("匹配规则个数超限");
			}
			if (filterRule.length() > MAX_RULE_LENGTH) {
				throw SOAException.FILTERRULE_TOO_MANY_EXCEPTION
						.newInstance("过滤规则个数超限");
			}

			route.setUsername((String) params.get("operator"));
			route.setOperator((String) params.get("operatorAddress"));
			route.setRule(routeRule.toString());
			if (StringUtils.isNotEmpty(extractToString(params.get("priority")))) {
				route.setPriority(Integer.parseInt(extractToString(params.get("priority"))));
			}
			routeService.createRoute(route);
		}
	}

	@Override
	public void updateRoute(Route route) {
		routeService.updateRoute(route);
	}

	@Override
	public void updateRoute(Route route, Map params) {
		String[] blacks = (String[]) params.get("black");
		boolean black = false;
		if (blacks != null && blacks.length > 0) {
			black = true;
		}

		Route oldRoute = routeService.findRoute(route.getId());
		if (null == oldRoute) {
			throw SOAException.ROUTERULE_NOT_EXISTS_EXCEPTION
					.newInstance("路由规则不存在");
		}

		// TODO 权限控制

		Map<String, String> when_name2valueList = Maps.newHashMap();
		Map<String, String> notWhen_name2valueList = Maps.newHashMap();
		for (String[] names : when_names) {
			when_name2valueList.put(names[0], extractToString(params.get(names[1])));
			notWhen_name2valueList.put(names[0], extractToString(params.get(names[2]))); // value不为null的情况，这里处理，后面会保证
		}

		Map<String, String> then_name2valueList = Maps.newHashMap();
		Map<String, String> notThen_name2valueList = Maps.newHashMap();
		for (String[] names : then_names) {
			then_name2valueList.put(names[0], extractToString(params.get(names[1])));
			notThen_name2valueList.put(names[0], extractToString(params.get(names[2])));
		}

		RouteRule routeRule = RouteRule.createFromNameAndValueListString(
				when_name2valueList, notWhen_name2valueList,
				then_name2valueList, notThen_name2valueList);

		RouteRule result = null;
		if (black) {
			RouteRule.MatchPair matchPair = routeRule.getThenCondition().get("black");
			Map<String, RouteRule.MatchPair> then = null;
			if (null == matchPair) {
				matchPair = new RouteRule.MatchPair();
				then = new HashMap<String, RouteRule.MatchPair>();
				then.put("black", matchPair);
			} else {
				matchPair.getMatches().clear();
			}
			matchPair.getMatches().add(String.valueOf(black));
			result = RouteRule.copyWithReplace(routeRule, null, then);
		}

		if (result == null) {
			result = routeRule;
		}

		if (result.getThenCondition().isEmpty()) {
			throw SOAException.FILTERRULE_CANOT_EMPTY_EXCEPTION
					.newInstance("过滤规则不能为空");
		}

		String matchRule = result.getWhenConditionString();
		String filterRule = result.getThenConditionString();

		//限制表达式的长度
		if (matchRule.length() > MAX_RULE_LENGTH) {
			throw SOAException.MATCHRULE_TOO_MANY_EXCEPTION
					.newInstance("匹配规则个数超限");
		}
		if (filterRule.length() > MAX_RULE_LENGTH) {
			throw SOAException.FILTERRULE_TOO_MANY_EXCEPTION
					.newInstance("过滤规则个数超限");
		}

		int priority = 0;
		if (StringUtils.isNotEmpty(extractToString(params.get("priority")))) {
			priority = Integer.parseInt(extractToString(params.get("priority")));
		}

		route.setRule(result.toString());
		route.setPriority(priority);
		route.setUsername((String) params.get("operator"));
		route.setOperator((String) params.get("operatorAddress"));
		route.setPriority(priority);
		route.setEnabled(oldRoute.isEnabled());
		routeService.updateRoute(route);

//			Set<String> usernames = Sets.newHashSet();
//			usernames.add((String) params.get("operator"));
//			usernames.add(route.getUsername());
		//RelateUserUtils.addOwnersOfService(usernames, route.getService(), ownerDAO);
	}

	/**
	 * 将请求参数转换回字符串
	 *
	 * @param collection
	 * @return
	 */
	private String extractToString(final Object collection) {
		if (collection instanceof Object[]) {
			StringBuffer sb = new StringBuffer(256);
			for (Object item : (Object[]) collection) {
				sb.append(extractToString(item)).append(",");
			}
			return sb.toString().substring(0, sb.length() - 1);
		} else if (null != collection) {
			return collection.toString();
		} else {
			return "";
		}
	}

	static void checkService(String service) {
		if (service.contains(",")) {
			throw new IllegalStateException("service(" + service + ") contain illegale ','");
		}

		String interfaceName = service;
		int gi = interfaceName.indexOf("/");
		if (gi != -1) interfaceName = interfaceName.substring(gi + 1);
		int vi = interfaceName.indexOf(':');
		if (vi != -1) interfaceName = interfaceName.substring(0, vi);

		if (interfaceName.indexOf('*') != -1 && interfaceName.indexOf('*') != interfaceName.length() - 1) {
			throw new IllegalStateException("service(" + service + ") only allow 1 *, and must be last char!");
		}
	}

	@Override
	public void deleteRoute(Long id) {
		routeService.deleteRoute(id);
	}

	@Override
	public void deleteRoute(Long[] ids) {
		for (Long id : ids) {
			routeService.deleteRoute(id);
		}
	}

	@Override
	public void enableRoute(Long id) {
		routeService.enableRoute(id);
	}

	@Override
	public void disableRoute(Long id) {
		routeService.disableRoute(id);
	}

	@Override
	public Route findRoute(Long id) {
		return routeService.findRoute(id);
	}

	@Override
	public List<Route> findAll() {
		return routeService.findAll();
	}

	@Override
	public List<Route> findByService(String serviceName) {
		return routeService.findByService(serviceName);
	}

	@Override
	public List<Route> findByAddress(String address) {
		return routeService.findByAddress(address);
	}

	@Override
	public List<Route> findByServiceAndAddress(String service, String address) {
		return routeService.findByServiceAndAddress(service, address);
	}

	@Override
	public List<Route> findForceRouteByService(String service) {
		return routeService.findForceRouteByService(service);
	}

	@Override
	public List<Route> findForceRouteByAddress(String address) {
		return routeService.findForceRouteByAddress(address);
	}

	@Override
	public List<Route> findForceRouteByServiceAndAddress(String service, String address) {
		return routeService.findForceRouteByServiceAndAddress(service, address);
	}

	@Override
	public List<Route> findAllForceRoute() {
		return routeService.findAllForceRoute();
	}

	@Override
	public boolean isInBlackList(Consumer consumer) {
		return routeService.isInBlackList(consumer);
	}

	@Override
	public void forbid(Long[] ids, String operator, String operatorAddress) {
		//		checkPrivilege(ids, operator);

		for (Long id : ids) {
			routeService.disableRoute(id);
		}
	}

	@Override
	public void allow(Long[] ids, String operator, String operatorAddress) {
//		checkPrivilege(ids, operator);

		for (Long id : ids) {
			routeService.enableRoute(id);
		}
	}

	@Override
	public Map<String, String> routeRuleParse(Long id) throws ParseException {
		Route route = routeService.findRoute(id);
		RouteRule routeRule = RouteRule.parse(route);

		@SuppressWarnings("unchecked")
		Map<String, RouteRule.MatchPair>[] paramArray = new Map[]{
				routeRule.getWhenCondition(), routeRule.getThenCondition()};
		String[][][] namesArray = new String[][][]{when_names, then_names};

		Map<String, String> routeRuleMap = Maps.newHashMap();
		for (int i = 0; i < paramArray.length; ++i) {
			Map<String, RouteRule.MatchPair> param = paramArray[i];
			String[][] names = namesArray[i];
			for (String[] name : names) {
				RouteRule.MatchPair matchPair = param.get(name[0]);
				if (matchPair == null) {
					continue;
				}

				if (!matchPair.getMatches().isEmpty()) {
					String m = RouteRule.join(matchPair.getMatches());
					routeRuleMap.put(name[1], m);
				}
				if (!matchPair.getUnmatches().isEmpty()) {
					String u = RouteRule.join(matchPair.getUnmatches());
					routeRuleMap.put(name[2], u);
				}
			}
		}
		return routeRuleMap;
	}

}
