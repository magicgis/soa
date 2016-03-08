package com.yeepay.g3.core.soa.center.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.AppAddressDao;
import com.yeepay.g3.core.soa.center.dao.NetworkDao;
import com.yeepay.g3.core.soa.center.entity.Network;
import com.yeepay.g3.core.soa.center.service.NetworkService;
import com.yeepay.g3.facade.soa.center.dto.NetworkDTO;
import com.yeepay.g3.facade.soa.center.enums.AppRoleEnum;
import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;
import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.facade.soa.center.utils.Tool;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.event.utils.DateUtils;

/**
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午7:01:08
 * @version:
 */
public class NetworkServiceImpl implements NetworkService {
	@Autowired
	private NetworkDao networkDao;

	@Autowired
	private AppAddressDao appAddressDao;

	@Override
	public void syncNetwork(String consumer, Map<String, Boolean> providers) {
		Date now = new Date();
		List<Network> statusList = networkDao.queryProviders(consumer);
		// 既有网络状态
		for (Network exist : statusList) {
			Boolean status = providers.remove(exist.getProvider());
			// 同一台机器不同的应用引用不同的服务，所以此处status == null不更新状态
			if (status != null) {
				// 网络状态变化才更新数据库
				// 超过10分钟才刷新一次到数据库
				if (!status.equals(exist.getStatus())
						|| DateUtils.getDiffMinutes(now,
								exist.getLastModifyDate()) >= 10) {
					exist.setStatus(status ? NetworkStatusEnum.ACTIVE
							: NetworkStatusEnum.DOWN);
					networkDao.update(exist);
				}
			}
		}

		// 新加入的网络访问
		for (String provider : providers.keySet()) {
			Network status = new Network();
			status.setConsumer(consumer);
			status.setProvider(provider);
			status.setStatus(providers.get(provider) ? NetworkStatusEnum.ACTIVE
					: NetworkStatusEnum.DOWN);
			networkDao.add(status);
		}
	}

	@Override
	public PageList queryNetwork(NetworkQueryParam param) {
		PageList result = networkDao.queryNetwork(param);
		for (Object o : result.getData()) {
			NetworkDTO network = (NetworkDTO) o;
			network.setConsumerAppList(appAddressDao.queryApps(
					network.getConsumer(), null));
			if (network.getConsumerAppList() == null
					|| network.getConsumerAppList().isEmpty()) {
				network.setConsumerAppList(appAddressDao.queryApps(
						Tool.getIP(network.getConsumer()), AppRoleEnum.CONSUMER));
			}
			network.setProviderAppList(appAddressDao.queryApps(
					network.getProvider(), null));
		}
		return result;
	}

	@Override
	public void deleteNetworkInfo(Long id) {
		networkDao.delete(id);
	}

	@Override
	public void clearUnknown() {
		networkDao.deleteByStatus(NetworkStatusEnum.UNKNOWN);
	}

	@Override
	public void syncUnknown() {
		// 超过15分钟未更新的记录认为是未知
		networkDao.syncUknown(DateUtils.addMinutes(new Date(), -15));
	}

	@Override
	public void offline(String address) {
		networkDao.offline(address);
	}
}
