package com.yeepay.g3.facade.soa.center.facade;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;

/**
 * 应用管理接口
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 下午2:33:32
 * @version:
 */
public interface AppMgtFacade {

	/**
	 * 新增or更新应用
	 */
	void saveApp(AppInfoDTO appInfoDTO);

	/**
	 * 下线应用，只修改应用状态，不真实删除数据
	 */
	void offlineApp(String appName);

	/**
	 * 重新启用应用
	 */
	void activeApp(String appName);

	/**
	 * 禁用启用应用
	 */
	void forbidApp(String appName);

	/**
	 * 删除应用
	 */
	void deleteApp(String appName);

	/**
	 * 查询应用
	 */
	AppInfoDTO findApp(String appName);

	/**
	 * 查询全部应用
	 */
	List<AppInfoDTO> queryAllApp(boolean isMonitor);

	void addOwner(AppOwnerDTO owner);

	void deleteOwner(Long id);

	void addUpgradeInfo(AppUpgradeInfoDTO upgradeInfo);

	void updateUpgradeInfo(AppUpgradeInfoDTO upgradeInfo);

	void deleteUpgradeInfo(Long id);

	void authApp(String appName, String toAppName);

	void unAuthApp(String appName, String toAppName);

	/**
	 * 获取所有应用名（动态）
	 *
	 * @return
	 */
	Set<String> findAllApplications();

	/**
	 * 获取应用直接依赖
	 *
	 * @param application
	 *            应用名
	 * @param reverse
	 *            false：依赖；true：被依赖
	 * @return
	 */
	Set<String> findDependencies(String application, boolean reverse);

	/**
	 * 获取应用直接或多级间接依赖
	 *
	 * @param application
	 *            应用名
	 * @param reverse
	 *            false：依赖；true：被依赖
	 * @param deep
	 *            依赖深度
	 * @return
	 */
	Map<String, Object> findDependencies(String application, boolean reverse,
			int deep);

	/**
	 * 迁移javadoc
	 */
	public void moveJavadoc(String source, String target);
}
