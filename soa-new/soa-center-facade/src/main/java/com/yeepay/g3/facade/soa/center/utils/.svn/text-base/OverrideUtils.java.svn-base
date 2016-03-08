package com.yeepay.g3.facade.soa.center.utils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.yeepay.g3.facade.soa.center.dubbo.domain.*;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Override;

import java.util.*;

public class OverrideUtils {

	public static List<Weight> overridesToWeights(List<Override> overrides) {
		List<Weight> weights = new ArrayList<Weight>();
		if (overrides == null) {
			return weights;
		}
		for (Override o : overrides) {
			if (StringUtils.isEmpty(o.getParams())) {
				continue;
			} else {
				Map<String, String> params = StringUtils.parseQueryString(o.getParams());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (entry.getKey().equals("weight")) {
						Weight weight = new Weight();
						weight.setAddress(o.getAddress());
						weight.setId(o.getId());
						weight.setService(o.getService());
						weight.setWeight(Integer.valueOf(entry.getValue()));
						weights.add(weight);
					}
				}
			}
		}
		return weights;
	}

	public static Weight overrideToWeight(Override override) {
		return overridesToWeights(Arrays.asList(override)).get(0);
	}

	public static Override weightToOverride(Weight weight) {
		Override override = new Override();
		override.setId(weight.getId());
		override.setAddress(weight.getAddress());
		override.setEnabled(true);
		override.setParams("weight=" + weight.getWeight());
		override.setService(weight.getService());
		return override;
	}

	public static List<LoadBalance> overridesToLoadBalances(List<Override> overrides) {
		List<LoadBalance> loadBalances = new ArrayList<LoadBalance>();
		if (overrides == null) {
			return loadBalances;
		}
		for (Override o : overrides) {
			if (StringUtils.isEmpty(o.getParams())) {
				continue;
			} else {
				Map<String, String> params = StringUtils.parseQueryString(o.getParams());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (entry.getKey().endsWith("loadbalance")) {
						LoadBalance loadBalance = new LoadBalance();
						String method = null;
						if (entry.getKey().endsWith(".loadbalance")) {
							method = entry.getKey().split(".loadbalance")[0];
						} else {
							method = "*";
						}

						loadBalance.setMethod(method);
						loadBalance.setId(o.getId());
						loadBalance.setService(o.getService());
						loadBalance.setStrategy(entry.getValue());
						loadBalances.add(loadBalance);

					}
				}
			}
		}
		return loadBalances;
	}

	public static LoadBalance overrideToLoadBalance(Override override) {
		return OverrideUtils.overridesToLoadBalances(Arrays.asList(override)).get(0);
	}

	public static Override loadBalanceToOverride(LoadBalance loadBalance) {
		Override override = new Override();
		override.setId(loadBalance.getId());
		override.setService(loadBalance.getService());
		override.setEnabled(true);
		String method = loadBalance.getMethod();
		String strategy = loadBalance.getStrategy();
		if (StringUtils.isEmpty(method) || method.equals("*")) {
			override.setParams("loadbalance=" + strategy);
		} else {
			override.setParams(method + ".loadbalance=" + strategy);
		}
		return override;
	}

	public static final Comparator<Override> OVERRIDE_COMPARATOR = new Comparator<Override>() {
		public int compare(Override o1, Override o2) {
			if (o1 == null && o2 == null) {
				return 0;
			}
			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			int cmp = cmp(o1.getAddress(), o2.getAddress());
			if (cmp != 0) {
				return cmp;
			}
			cmp = cmp(o1.getApplication(), o2.getApplication());
			if (cmp != 0) {
				return cmp;
			}
			return cmp(o1.getService(), o2.getService());
		}

		private int cmp(String s1, String s2) {
			if (s1 == null && s2 == null) {
				return 0;
			}
			if (s1 == null) {
				return -1;
			}
			if (s2 == null) {
				return 1;
			}
			if (s1.equals(s2)) {
				return 0;
			}
			if (isAny(s1)) {
				return 1;
			}
			if (isAny(s2)) {
				return -1;
			}
			return s1.compareTo(s2);
		}

		private boolean isAny(String s) {
			return s == null || s.length() == 0 || Constants.ANY_VALUE.equals(s) || Constants.ANYHOST_VALUE.equals(s);
		}
	};

	public static void setConsumerOverrides(Consumer consumer, List<Override> overrides) {
		if (consumer == null || overrides == null) {
			return;
		}
		List<Override> result = new ArrayList<Override>(overrides.size());
		for (Override override : overrides) {
			if (!override.isEnabled()) {
				continue;
			}
			if (override.isMatch(consumer)) {
				result.add(override);
			}
			if (override.isUniqueMatch(consumer)) {
				consumer.setOverride(override);
			}
		}
		Collections.sort(result, OverrideUtils.OVERRIDE_COMPARATOR);
		consumer.setOverrides(result);
	}

	public static void setProviderOverrides(Provider provider, List<Override> overrides) {
		if (provider == null || overrides == null) {
			return;
		}
		List<Override> result = new ArrayList<Override>(overrides.size());
		for (Override override : overrides) {
			if (!override.isEnabled()) {
				continue;
			}
			if (override.isMatch(provider)) {
				result.add(override);
			}
			if (override.isUniqueMatch(provider)) {
				provider.setOverride(override);
			}
		}
		provider.setOverrides(overrides);
	}

}
