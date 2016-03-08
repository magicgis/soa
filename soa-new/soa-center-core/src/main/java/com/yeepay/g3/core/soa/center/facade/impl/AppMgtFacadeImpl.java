package com.yeepay.g3.core.soa.center.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yeepay.g3.core.soa.center.biz.RegistrySyncBiz;
import com.yeepay.g3.core.soa.center.dubbo.service.ConsumerService;
import com.yeepay.g3.core.soa.center.dubbo.service.ProviderService;
import com.yeepay.g3.core.soa.center.service.AppInfoService;
import com.yeepay.g3.core.soa.center.service.AppOwnerService;
import com.yeepay.g3.core.soa.center.service.AppUpgradeInfoService;
import com.yeepay.g3.core.soa.center.service.ServiceInfoService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.core.soa.center.utils.SoaConfigUtils;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.AppOwnerDTO;
import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Consumer;
import com.yeepay.g3.facade.soa.center.dubbo.domain.Provider;
import com.yeepay.g3.facade.soa.center.enums.AuthStatusEnum;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.ConsumerFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;

/**
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 下午3:45:39
 * @version:
 */
@Service
@Transactional
public class AppMgtFacadeImpl implements AppMgtFacade {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppMgtFacadeImpl.class);

	@Autowired
	private AppInfoService appInfoService;

	@Autowired
	private ServiceInfoService serviceInfoService;

	@Autowired
	private AppOwnerService appOwnerService;

	@Autowired
	private AppUpgradeInfoService appUpgradeInfoService;

	@Autowired
	private ProviderService providerService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ConsumerFacade consumerFacade;

	@Autowired
	private RegistrySyncBiz registrySyncBiz;

	@Override
	public void saveApp(AppInfoDTO appInfoDTO) {
		CheckUtils.notNull(appInfoDTO, "appInfoDTO");
		CheckUtils.notEmpty(appInfoDTO.getAppName(), "appName");
		CheckUtils.notEmpty(appInfoDTO.getAppTitle(), "appTitle");
		CheckUtils.notEmpty(appInfoDTO.getAppDesc(), "appDesc");
		appInfoService.saveAppInfo(appInfoDTO);
	}

	@Override
	public void offlineApp(String appName) {
		CheckUtils.notEmpty(appName, "appName");
		appInfoService.changeStatus(appName, SoaStatusEnum.OFFLINE);
		serviceInfoService.changeStatusByApp(appName, SoaStatusEnum.OFFLINE);
		this.forbidAll(appName);
	}

	@Override
	public void activeApp(String appName) {
		CheckUtils.notEmpty(appName, "appName");
		appInfoService.changeStatus(appName, SoaStatusEnum.ACTIVE);
		serviceInfoService.changeStatusByApp(appName, SoaStatusEnum.ACTIVE);
		this.activeAll(appName);
	}

	@Override
	public void forbidApp(String appName) {
		CheckUtils.notEmpty(appName, "appName");
		appInfoService.changeStatus(appName, SoaStatusEnum.FORBID);
		serviceInfoService.changeStatusByApp(appName, SoaStatusEnum.FORBID);
		this.forbidAll(appName);
	}

	@Override
	public void deleteApp(String appName) {
		CheckUtils.notEmpty(appName, "appName");
		serviceInfoService.deleteByApp(appName);
		appInfoService.deleteApp(appName);
		this.forbidAll(appName);
	}

	@Override
	public AppInfoDTO findApp(String appName) {
		if (StringUtils.isBlank(appName)) {
			return null;
		}
		return appInfoService.findApp(appName);
	}

	@Override
	public List<AppInfoDTO> queryAllApp(boolean isMonitor) {
		if (!isMonitor) {
			return appInfoService.queryAll();
		} else {
			Set<String> applications = this.findAllApplications();
			List<AppInfoDTO> appList = Lists.newArrayList();
			for (String appName : applications) {
				AppInfoDTO app = new AppInfoDTO();
				app.setAppName(appName);
				app.setDepAppCount(this.findDependencies(appName, false).size());
				app.setDepByAppCount(this.findDependencies(appName, true)
						.size());
				app.setExpServiceCount(this.providerService.findByApplication(
						appName).size());
				app.setRefServiceCount(this.consumerService.findByApplication(
						appName).size());
				if (app.getExpServiceCount() > 0) {
					app.setAddressCount(Sets
							.newHashSet(
									providerService
											.findAddressesByApplication(appName))
							.size());
				} else {
					app.setAddressCount(Sets
							.newHashSet(
									consumerService
											.findAddressesByApplication(appName))
							.size());
				}
				app.setRole(registrySyncBiz.calcAppRole(app));
				appList.add(app);
			}
			DataConvertUtils.sort(appList, "appName");
			return appList;
		}
	}

	@Override
	public void addOwner(AppOwnerDTO owner) {
		CheckUtils.notNull(owner, "owner");
		CheckUtils.notEmpty(owner.getAppName(), "appName");
		CheckUtils.notEmpty(owner.getLoginName(), "loginName");
		CheckUtils.notEmpty(owner.getOwnerName(), "ownerName");
		appOwnerService.addOwner(owner);
	}

	@Override
	public void deleteOwner(Long id) {
		if (id == null) {
			return;
		}
		appOwnerService.delOwner(id);
	}

	@Override
	public void addUpgradeInfo(AppUpgradeInfoDTO upgradeInfo) {
		CheckUtils.notNull(upgradeInfo, "upgradeInfo");
		CheckUtils.notEmpty(upgradeInfo.getAppName(), "appName");
		CheckUtils.notEmpty(upgradeInfo.getUpgradeInfo(), "upgradeInfo");
		appUpgradeInfoService.addUpgradeInfo(upgradeInfo);
	}

	@Override
	public void updateUpgradeInfo(AppUpgradeInfoDTO upgradeInfo) {
		CheckUtils.notNull(upgradeInfo, "upgradeInfo");
		CheckUtils.notEmpty(upgradeInfo.getId(), "id");
		CheckUtils.notEmpty(upgradeInfo.getUpgradeInfo(), "upgradeInfo");
		appUpgradeInfoService.updateUpgradeInfo(upgradeInfo);
	}

	@Override
	public void deleteUpgradeInfo(Long id) {
		if (id == null) {
			return;
		}
		appUpgradeInfoService.delUpgradeInfo(id);
	}

	@Override
	public void authApp(String appName, String toAppName) {
		CheckUtils.notEmpty(appName, "appName");
		CheckUtils.notEmpty(toAppName, "toAppName");
		appInfoService.changeAuthStatus(appName, toAppName,
				AuthStatusEnum.AUTHED);
	}

	@Override
	public void unAuthApp(String appName, String toAppName) {
		CheckUtils.notEmpty(appName, "appName");
		CheckUtils.notEmpty(toAppName, "toAppName");
		appInfoService.changeAuthStatus(appName, toAppName,
				AuthStatusEnum.FORBID);
	}

	private void forbidAll(String application) {
		List<Provider> providers = providerService
				.findByApplication(application);
		for (Provider p : providers) {
			try {
				providerService.disableProvider(p.getId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		List<Consumer> consumers = consumerFacade
				.findByApplication(application);
		List<Long> ids = new ArrayList<Long>();
		for (Consumer c : consumers) {
			ids.add(c.getId());
		}
		try {
			consumerFacade.forbid(ids.toArray(new Long[0]), "system");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	private void activeAll(String application) {
		List<Provider> providers = providerService
				.findByApplication(application);
		for (Provider p : providers) {
			try {
				providerService.enableProvider(p.getId());
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		List<Consumer> consumers = consumerFacade
				.findByApplication(application);
		List<Long> ids = new ArrayList<Long>();
		for (Consumer c : consumers) {
			ids.add(c.getId());
		}
		try {
			consumerFacade.allow(ids.toArray(new Long[0]), "system");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public Set<String> findAllApplications() {
		return registrySyncBiz.findAllApplications();
	}

	@Override
	public Set<String> findDependencies(String application, boolean reverse) {
		CheckUtils.notEmpty(application, "application");
		return registrySyncBiz.findDependencies(application, reverse);
	}

	@Override
	public Map<String, Object> findDependencies(String application,
			boolean reverse, int deep) {
		CheckUtils.notEmpty(application, "application");
		Map<String, Object> deps = Maps.newHashMap();
		if (deep > 0) {
			Set<String> directly = registrySyncBiz.findDependencies(
					application, reverse);
			for (String app : directly) {
				deps.put(app, this.findDependencies(app, reverse, deep - 1));
			}
		}
		return deps;
	}

	@Override
	public void moveJavadoc(String source, String target) {
		CheckUtils.notEmpty(target, "new javadoc root path");
		if (StringUtils.isBlank(source)) {
			source = SoaConfigUtils.getJavadocCtxPath(null);
		}
		CheckUtils.notEmpty(source, "old javadoc root path");
		if (!StringUtils.endsWith(source, "/")) {
			source += "/";
		}
		if (!StringUtils.endsWith(target, "/")) {
			target += "/";
		}
		appInfoService.moveJavadoc(source, target);
	}
}
