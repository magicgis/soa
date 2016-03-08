package com.yeepay.g3.core.soa.center.facade.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.center.dubbo.service.RouteService;
import com.yeepay.g3.core.soa.center.dubbo.utils.route.RouteRule;
import com.yeepay.g3.core.soa.center.utils.IPUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Access;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Route;
import com.yeepay.g3.facade.soa.center.exceptions.SOAException;
import com.yeepay.g3.facade.soa.center.facade.AccessFacade;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;

/**
 * Title: 访问控制 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 10:47
 */
@Service
public class AccessFacadeImpl implements AccessFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccessFacade.class);

	@Autowired
	private RouteService routeService;

	@Override
	public List<Access> findByService(String service) throws ParseException {
		List<Route> routes = routeService.findForceRouteByService(service);
		return findByRoute(routes);
	}

	@Override
	public List<Access> findByAddress(String address) throws ParseException {
		List<Route> routes = routeService.findForceRouteByAddress(address);
		return findByRoute(routes);
	}

	@Override
	public List<Access> findAll() throws ParseException {
		List<Route> routes = routeService.findAll();
		return findByRoute(routes);
	}

	private List<Access> findByRoute(List<Route> routes) throws ParseException {
		List<Access> accesses = new ArrayList<Access>();
		if (null == routes) {
			return accesses;
		}

		for (Route route : routes) {
			Map<String, RouteRule.MatchPair> rule = RouteRule.parseRule(route.getMatchRule());
			RouteRule.MatchPair pair = rule.get("consumer.host");
			if (pair != null) {
				for (String host : pair.getMatches()) {
					Access access = new Access();
					access.setAddress(host);
					access.setService(route.getService());
					access.setAllow(false);
					accesses.add(access);
				}
				for (String host : pair.getUnmatches()) {
					Access access = new Access();
					access.setAddress(host);
					access.setService(route.getService());
					access.setAllow(true);
					accesses.add(access);
				}
			}
		}
		return accesses;
	}

	@Override
	public void create(String service, String address, boolean allow, String operator) throws IOException, ParseException {
		LOGGER.info("{} 创建访问控制信息 service:{} address:{} allow:{}", operator, service, address, allow);

		Set<String> consumerAddresses = toAddr(address);
		Set<String> aimServices = toService(service);
		for (String aimService : aimServices) {
			boolean isFirst = false;
			List<Route> routes = routeService.findForceRouteByService(aimService);
			Route route = null;
			if (routes == null || routes.size() == 0) {
				isFirst = true;
				route = new Route();
				route.setService(aimService);
				route.setForce(true);
				route.setName(aimService + " blackwhitelist");
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

			for (String consumerAddress : consumerAddresses) {
				if (allow) {
					matchPair.getUnmatches().add(Tool.getIP(consumerAddress));
				} else {
					matchPair.getMatches().add(Tool.getIP(consumerAddress));
				}
			}
			StringBuilder sb = new StringBuilder();
			RouteRule.contidionToString(sb, when);
			route.setMatchRule(sb.toString());
			route.setUsername(operator);
			if (isFirst) {
				routeService.createRoute(route);
			} else {
				routeService.updateRoute(route);
			}
		}
	}

	private Set<String> toAddr(String addr) throws IOException {
		Set<String> consumerAddresses = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new StringReader(addr));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;
				if (!IPUtils.IP_PATTERN.matcher(s).matches()) {
					throw new IllegalStateException("illegal IP: " + s);
				}
				if (IPUtils.LOCAL_IP_PATTERN.matcher(s).matches() || IPUtils.ALL_IP_PATTERN.matcher(s).matches()) {
					throw new IllegalStateException("local IP or any host ip is illegal: " + s);
				}

				consumerAddresses.add(s);
			}
		}
		return consumerAddresses;
	}

	private Set<String> toService(String services) throws IOException {
		Set<String> aimServices = new HashSet<String>();
		BufferedReader reader = new BufferedReader(new StringReader(services));
		while (true) {
			String line = reader.readLine();
			if (null == line)
				break;

			String[] split = line.split("[\\s,;]+");
			for (String s : split) {
				if (s.length() == 0)
					continue;
				aimServices.add(s);
			}
		}
		return aimServices;
	}

	@Override
	public void deleteByServiceAndAddress(List<String> serviceList, List<String> addressList, String operator) throws ParseException {
		LOGGER.info("{} 删除访问控制信息 serviceList:{} addressList:{}", operator, serviceList, addressList);

		if (null == serviceList || null == addressList || serviceList.size() != addressList.size()) {
			throw SOAException.ILLEGAL_ARGS_EXCEPTION.newInstance("参数不合法");
		}

		Map<String, Set<String>> prepareToDeleate = new HashMap<String, Set<String>>();
		for (int index = 0; index < serviceList.size(); ++index) {
			String service = serviceList.get(index);
			String address = addressList.get(index);
			Set<String> addresses = prepareToDeleate.get(service);
			if (addresses == null) {
				prepareToDeleate.put(service, new HashSet<String>());
				addresses = prepareToDeleate.get(service);
			}
			addresses.add(address);
		}

		for (Map.Entry<String, Set<String>> entry : prepareToDeleate.entrySet()) {
			String service = entry.getKey();
			List<Route> routes = routeService.findForceRouteByService(service);
			if (routes == null || routes.size() == 0) {
				continue;
			}
			for (Route blackwhitelist : routes) {
				RouteRule.MatchPair pairs = null;
				pairs = RouteRule.parseRule(blackwhitelist.getMatchRule()).get("consumer.host");
				Set<String> matches = new HashSet<String>();
				matches.addAll(pairs.getMatches());
				Set<String> unmatches = new HashSet<String>();
				unmatches.addAll(pairs.getUnmatches());
				for (String pair : pairs.getMatches()) {
					for (String address : entry.getValue()) {
						if (pair.equals(address)) {
							matches.remove(pair);
							break;
						}
					}
				}
				for (String pair : pairs.getUnmatches()) {
					for (String address : entry.getValue()) {
						if (pair.equals(address)) {
							unmatches.remove(pair);
							break;
						}
					}
				}
				if (matches.size() == 0 && unmatches.size() == 0) {
					routeService.deleteRoute(blackwhitelist.getId());
				} else {
					Map<String, RouteRule.MatchPair> condition = new HashMap<String, RouteRule.MatchPair>();
					condition.put("consumer.host", new RouteRule.MatchPair(matches, unmatches));
					StringBuilder sb = new StringBuilder();
					RouteRule.contidionToString(sb, condition);
					blackwhitelist.setMatchRule(sb.toString());
					routeService.updateRoute(blackwhitelist);
				}
			}
		}
	}

}
