package com.yeepay.g3.core.soa.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.yeepay.g3.core.soa.center.dao.AppDependencyDao;
import com.yeepay.g3.core.soa.center.dao.AppInfoDao;
import com.yeepay.g3.core.soa.center.dao.AppOwnerDao;
import com.yeepay.g3.core.soa.center.dao.AppUpgradeInfoDao;
import com.yeepay.g3.core.soa.center.entity.AppDependency;
import com.yeepay.g3.core.soa.center.entity.AppInfo;
import com.yeepay.g3.core.soa.center.service.AppInfoService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.core.soa.center.utils.SoaConfigUtils;
import com.yeepay.g3.facade.soa.center.dto.AppDependencyDTO;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;
import com.yeepay.g3.facade.soa.center.enums.AuthStatusEnum;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.cache.remote.RemoteCacheUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * 应用信息
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:39:02
 * @version:
 */
@Transactional
public class AppInfoServiceImpl implements AppInfoService {
	@Autowired
	private AppInfoDao appInfoDao;

	@Autowired
	private AppDependencyDao appDependencyDao;

	@Autowired
	private AppUpgradeInfoDao appUpgradeInfoDao;

	@Autowired
	private AppOwnerDao appOwnerDao;

	@Override
	public void syncAppInfo(AppInfoDTO app) {
		AppInfo exist = appInfoDao.get(app.getAppName());
		if (exist == null) {
			// 新增
			if (app.getAppName().indexOf("-hessian") > 0) {
				app.setJavaDoc(SoaConfigUtils.getJavadocCtxPath(null)
						+ app.getAppName().replace("-hessian", ""));
			}
			appInfoDao.add(DataConvertUtils.convert(app, AppInfo.class));
		} else {
			// 更新
			exist.setAddressCount(app.getAddressCount());
			exist.setDepAppCount(app.getDepAppCount());
			exist.setDepByAppCount(app.getDepByAppCount());
			exist.setExpServiceCount(app.getExpServiceCount());
			exist.setRefServiceCount(app.getRefServiceCount());
			exist.setRole(app.getRole());
			exist.setJavaDoc(app.getJavaDoc());
			if (exist.getStatus() != SoaStatusEnum.FORBID) {
				exist.setStatus(SoaStatusEnum.ACTIVE);
			}
			appInfoDao.update(exist);
		}
	}

	@Override
	public void syncDependency(List<AppDependencyDTO> dependencyList) {
		if (dependencyList == null) {
			return;
		}

		for (AppDependencyDTO depDTO : dependencyList) {
			if (StringUtils.isBlank(depDTO.getAppName())
					|| StringUtils.isBlank(depDTO.getDepAppName())) {
				continue;
			}
			AppDependency dep = appDependencyDao.find(depDTO.getAppName(),
					depDTO.getDepAppName());
			if (dep == null) {
				dep = DataConvertUtils.convert(depDTO, AppDependency.class);
				appDependencyDao.add(dep);
			}
		}
	}

	@Override
	public void saveAppInfo(AppInfoDTO app) {
		if (app == null) {
			return;
		}
		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey(SoaCenterConst.CACHE_APP_INFO));
		if (appInfoDao.get(app.getAppName()) == null) {
			appInfoDao.add(DataConvertUtils.convert(app, AppInfo.class));
		} else {
			appInfoDao.updateBaseInfo(DataConvertUtils.convert(app,
					AppInfo.class));
		}
	}

	@Override
	public void initForSync() {
		appInfoDao
				.batchChangeStatus(SoaStatusEnum.ACTIVE, SoaStatusEnum.UNSYNC);
	}

	@Override
	public void syncDownApp() {
		appInfoDao.batchChangeDown();
	}

	@Override
	public void changeStatus(String appName, SoaStatusEnum status) {
		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey(SoaCenterConst.CACHE_APP_INFO));
		appInfoDao.changeStatus(appName, status);
	}

	@Override
	public void deleteApp(String appName) {
		RemoteCacheUtils.remove(RemoteCacheUtils
				.generateCacheKey(SoaCenterConst.CACHE_APP_INFO));
		appInfoDao.delete(appName);
		appDependencyDao.deleteAllByApp(appName);
		appOwnerDao.deleteAllByApp(appName);
		appUpgradeInfoDao.deleteAllByApp(appName);
	}

	@Override
	public AppInfoDTO findApp(String appName) {
		AppInfoDTO appInfo = DataConvertUtils.convert(appInfoDao.get(appName),
				AppInfoDTO.class);
		this.appendDetailInfo(appInfo);
		return appInfo;
	}

	@Override
	public List<AppInfoDTO> queryAll() {
		List<AppInfo> apps = this.appInfoDao.getAll();
		List<AppInfoDTO> appInfoList = DataConvertUtils.convert(apps,
				AppInfoDTO.class);
		for (AppInfoDTO app : appInfoList) {
			this.appendDetailInfo(app);
		}
		return appInfoList;
	}

	@Override
	public PageList queryApp(ServiceQueryParam queryParam) {
		PageList appInfoList = this.appInfoDao.queryAppInfo(queryParam);
		for (Object app : appInfoList) {
			this.appendDetailInfo((AppInfoDTO) app);
		}
		return appInfoList;
	}

	private void appendDetailInfo(AppInfoDTO appInfo) {
		if (appInfo == null) {
			return;
		}
		String appName = appInfo.getAppName();
		appInfo.setOwnerList(DataConvertUtils.convert(
				appOwnerDao.queryByApp(appName), AppOwnerDTO.class));
		appInfo.setDepAppList(DataConvertUtils.convert(
				appDependencyDao.queryDependency(appName),
				AppDependencyDTO.class));
		appInfo.setDepByAppList(DataConvertUtils.convert(
				appDependencyDao.queryReference(appName),
				AppDependencyDTO.class));
		appInfo.setLatestUpgrade(DataConvertUtils.convert(
				appUpgradeInfoDao.findLatestUpgrade(appName),
				AppUpgradeInfoDTO.class));
	}

	@Override
	public void changeAuthStatus(String appName, String toAppName,
			AuthStatusEnum authStatus) {
		AppDependency dep = appDependencyDao.find(toAppName, appName);
		if (dep != null) {
			dep.setAuthStatus(authStatus);
			appDependencyDao.update(dep);
		} else {
			dep = new AppDependency();
			dep.setAppName(toAppName);
			dep.setDepAppName(appName);
			dep.setAuthStatus(authStatus);
			appDependencyDao.add(dep);
		}
	}

	@Override
	public void moveJavadoc(String source, String target) {
		appInfoDao.moveJavadoc(source, target);
	}
}
