package com.yeepay.g3.core.soa.center.service;

import java.util.List;

import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.AppDependencyDTO;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.AuthStatusEnum;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.cache.config.Cache;
import com.yeepay.g3.utils.cache.config.CacheTypeEnum;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * 应用信息
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:39:02
 * @version:
 */
public interface AppInfoService {
	/**
	 * 同步前置状态为待同步
	 */
	void initForSync();

	/**
	 * 同步完成后，仍为待同步的应用标记为宕机
	 */
	void syncDownApp();

	void syncAppInfo(AppInfoDTO app);

	void syncDependency(List<AppDependencyDTO> dependencyList);

	void saveAppInfo(AppInfoDTO app);

	void changeStatus(String appName, SoaStatusEnum status);

	void deleteApp(String appName);

	AppInfoDTO findApp(String appName);

	@Cache(name = SoaCenterConst.CACHE_APP_INFO, type = CacheTypeEnum.REMOTE)
	List<AppInfoDTO> queryAll();

	PageList queryApp(ServiceQueryParam queryParam);

	void changeAuthStatus(String appName, String toAppName,
			AuthStatusEnum authStatus);

	void moveJavadoc(String source, String target);
}
