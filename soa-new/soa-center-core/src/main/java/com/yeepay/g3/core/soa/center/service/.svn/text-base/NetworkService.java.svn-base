package com.yeepay.g3.core.soa.center.service;

import java.util.Map;

import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * 网络状态
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:16:10
 * @version:
 */
public interface NetworkService {
	void syncNetwork(String consumer, Map<String, Boolean> providers);

	PageList queryNetwork(NetworkQueryParam param);

	void deleteNetworkInfo(Long id);

	void clearUnknown();

	void offline(String address);

	void syncUnknown();
}
