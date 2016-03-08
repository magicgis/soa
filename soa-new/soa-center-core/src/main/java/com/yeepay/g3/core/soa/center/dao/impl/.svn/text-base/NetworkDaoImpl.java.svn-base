package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.NetworkDao;
import com.yeepay.g3.core.soa.center.entity.Network;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.NetworkDTO;
import com.yeepay.g3.facade.soa.center.enums.NetworkStatusEnum;
import com.yeepay.g3.facade.soa.center.param.NetworkQueryParam;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;
import com.yeepay.g3.utils.query.QueryParam;
import com.yeepay.g3.utils.query.QueryResult;
import com.yeepay.g3.utils.query.QueryService;

/**
 * 网络状态
 *
 * @author：wang.bao
 * @since：2014年9月19日 下午6:17:31
 * @version:
 */
public class NetworkDaoImpl extends GenericDaoDefault<Network> implements
		NetworkDao {
	@Autowired
	private QueryService queryService;

	@Override
	public Network find(String consumer, String provider) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("consumer", consumer);
		params.put("provider", provider);
		return (Network) this.queryOne("find", params);
	}

	@Override
	public void deleteByAddress(String address) {
		this.delete("deleteByAddress", address);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Network> queryProviders(String consumer) {
		return this.query("queryProviders", consumer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Network> queryCustomers(String provider) {
		return this.query("queryCustomers", provider);
	}

	@Override
	public PageList queryNetwork(NetworkQueryParam queryParam) {
		List<ResultMapping> mappings = this.getSqlSession().getConfiguration()
				.getResultMap("networkMap").getResultMappings();
		if (queryParam == null) {
			queryParam = new NetworkQueryParam();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				"address",
				StringUtils.trimToNull(StringUtils.replace(
						queryParam.getAddress(), ".*", "%")));
		params.put("appName", StringUtils.trimToNull(queryParam.getAppName()));
		params.put("type", queryParam.getType());
		params.put("status", queryParam.getStatus());

		QueryParam param = new QueryParam();
		Integer startIndex = (queryParam.getPageNo() - 1)
				* queryParam.getPageSize() + 1;
		param.setStartIndex(startIndex);
		param.setMaxSize(queryParam.getPageSize());
		param.setParams(params);
		QueryResult queryResult = this.queryService
				.query("queryNetwork", param);
		return DataConvertUtils
				.convert(queryResult, mappings, NetworkDTO.class);
	}

	@Override
	public void syncUknown(Date lastModifyDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", NetworkStatusEnum.UNKNOWN);
		params.put("lastModifyDate", lastModifyDate);
		this.update("syncUknown", params);
	}

	@Override
	public void deleteByStatus(NetworkStatusEnum status) {
		this.delete("deleteByStatus", status.name());
	}

	@Override
	public void offline(String address) {
		this.delete("deleteAllByAddress", address);
	}
}
