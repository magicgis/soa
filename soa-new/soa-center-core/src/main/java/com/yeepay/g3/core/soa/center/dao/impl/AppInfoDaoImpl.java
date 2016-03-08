package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.AppInfoDao;
import com.yeepay.g3.core.soa.center.entity.AppInfo;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;
import com.yeepay.g3.utils.query.QueryParam;
import com.yeepay.g3.utils.query.QueryResult;
import com.yeepay.g3.utils.query.QueryService;

/**
 * 应用信息
 *
 * @author： wang.bao
 * @since：2014年7月3日 上午11:39:02
 * @version:
 */
public class AppInfoDaoImpl extends GenericDaoDefault<AppInfo> implements
		AppInfoDao {
	@Autowired
	private QueryService queryService;

	@Override
	public void changeStatus(String appName, SoaStatusEnum status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("status", status);
		this.update("changeStatus", params);
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
	public void batchChangeDown() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromStatus", SoaStatusEnum.UNSYNC);
		params.put("toStatus", SoaStatusEnum.DOWN);
		this.update("batchChangeDown", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppInfo> queryApp(String keyWord) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		return this.query("queryApp", params);
	}

	@Override
	public void updateStatus(String appName, SoaStatusEnum status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appName", appName);
		params.put("status", status);
		this.update("updateStatus", params);
	}

	@Override
	public PageList queryAppInfo(ServiceQueryParam queryParam) {
		List<ResultMapping> mappings = this.getSqlSession().getConfiguration()
				.getResultMap("appInfoMap").getResultMappings();
		Map<String, Object> extParam = queryParam.getExtParam();
		extParam.put("queryInput", queryParam.getQueryInput());

		QueryParam param = new QueryParam();
		Integer startIndex = (queryParam.getPageNo() - 1)
				* queryParam.getPageSize() + 1;
		param.setStartIndex(startIndex);
		param.setMaxSize(queryParam.getPageSize());
		param.setParams(extParam);
		QueryResult queryResult = this.queryService
				.query("queryAppInfo", param);
		return DataConvertUtils
				.convert(queryResult, mappings, AppInfoDTO.class);
	}

	@Override
	public void updateBaseInfo(AppInfo appInfo) {
		this.update("updateBaseInfo", appInfo);
	}

	@Override
	public void moveJavadoc(String source, String target) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("source", source);
		params.put("target", target);
		this.update("moveJavadoc", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppInfo> queryByAddress(String address) {
		return this.query("queryByAddress", address);
	}
}
