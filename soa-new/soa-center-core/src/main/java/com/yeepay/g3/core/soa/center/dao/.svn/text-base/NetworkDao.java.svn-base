package com.yeepay.g3.core.soa.center.dao;

import java.util.Date;
import java.util.List;

import com.yeepay.g3.core.soa.center.entity.Network;
import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;
import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * 网络状态
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:16:10
 * @version:
 */
public interface NetworkDao extends GenericDao<Network> {
	Network find(String consumer, String provider);

	void deleteByAddress(String address);

	/**
	 * 查询提供者
	 *
	 * @param consumer
	 * @return
	 */
	List<Network> queryProviders(String consumer);

	/**
	 * 查询消费者
	 *
	 * @param provider
	 * @return
	 */
	List<Network> queryCustomers(String provider);

	PageList queryNetwork(NetworkQueryParam param);

	void syncUknown(Date lastModifyDate);

	void deleteByStatus(NetworkStatusEnum status);

	/**
	 * 下线机器，忽略端口号
	 *
	 * @param address
	 */
	void offline(String address);
}
