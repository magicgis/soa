package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.AppInfo;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 * 应用信息
 *
 * @author：wang.bao
 * @since：2014年7月3日 上午11:39:02
 * @version:
 */
public interface AppInfoDao extends GenericDao<AppInfo> {
	void changeStatus(String appName, SoaStatusEnum status);

	void batchChangeStatus(SoaStatusEnum fromStatus, SoaStatusEnum toStatus);

	void batchChangeDown();

	List<AppInfo> queryApp(String keyWord);

	void updateStatus(String appName, SoaStatusEnum status);

	PageList queryAppInfo(ServiceQueryParam queryParam);

	/**
	 * 只更新描述等基本信息
	 *
	 * @param appInfo
	 */
	void updateBaseInfo(AppInfo appInfo);

	void moveJavadoc(String source, String target);

	/**
	 * 查询机器所部署的应用
	 *
	 * @param address
	 * @return
	 */
	List<AppInfo> queryByAddress(String address);
}
