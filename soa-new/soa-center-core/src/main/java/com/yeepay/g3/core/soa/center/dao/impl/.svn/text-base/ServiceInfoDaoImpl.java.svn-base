package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.ServiceInfoDao;
import com.yeepay.g3.core.soa.center.entity.ServiceInfo;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;
import com.yeepay.g3.utils.query.QueryParam;
import com.yeepay.g3.utils.query.QueryResult;
import com.yeepay.g3.utils.query.QueryService;

/**
 * @author：wang.bao
 * @since：2014年7月29日 上午10:23:04
 * @version:
 */
public class ServiceInfoDaoImpl extends GenericDaoDefault<ServiceInfo>
		implements ServiceInfoDao {
	@Autowired
	private QueryService queryService;

	@Override
	public ServiceInfo find(String serviceInterface) {
		return (ServiceInfo) this.queryOne("find", serviceInterface);
	}

	@Override
	public void batchChangeStatus(SoaStatusEnum fromStatus,
			SoaStatusEnum toStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromStatus", fromStatus);
		params.put("toStatus", toStatus);
		this.update("batchChangeStatus", params);
	}

	@Override
	public PageList queryServiceInfo(ServiceQueryParam queryParam) {
		List<ResultMapping> mappings = this.getSqlSession().getConfiguration()
				.getResultMap("serviceInfoMap").getResultMappings();
		Map<String, Object> extParam = queryParam.getExtParam();
		extParam.put("queryInput", queryParam.getQueryInput());

		QueryParam param = new QueryParam();
		Integer startIndex = (queryParam.getPageNo() - 1)
				* queryParam.getPageSize() + 1;
		param.setStartIndex(startIndex);
		param.setMaxSize(queryParam.getPageSize());
		param.setParams(extParam);
		QueryResult queryResult = this.queryService.query("queryServiceInfo",
				param);
		return DataConvertUtils
				.convert(queryResult, mappings, ServiceInfoDTO.class);
	}

	@Override
	public void changeStatusByApp(String appName, SoaStatusEnum status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("status", status);
		this.update("changeStatusByApp", params);
	}

	@Override
	public void deleteByApp(String appName) {
		this.update("deleteByApp", appName);
	}

	@Override
	public void changeStatus(SoaStatusEnum status, Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("status", status);
		this.update("changeStatus", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceInfo> queryByApp(String appName) {
		return this.query("queryByApp", appName);
	}
}
