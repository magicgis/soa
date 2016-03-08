package com.yeepay.g3.app.soa.center.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.app.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.AppUpgradeInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.event.ext.BaseEventUtils;

/**
 * 应用升级信息控制器
 *
 * @author：wang.bao
 * @since：2014年8月22日 下午2:10:39
 * @version:
 */
@Controller
@RequestMapping("/upgrades")
public class UpgradesController extends BaseController {
	@Reference
	private AppMgtFacade appMgtFacade;

	/**
	 * 新增升级信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addUpgradeInfo(
			@RequestParam(value = "upgradeId", required = false) Long upgradeId,
			@RequestParam(value = "appName", required = false) String appName,
			@RequestParam(value = "upgradeInfo", required = true) String upgradeInfo,
			@RequestParam(value = "mail", required = false) Boolean mail,
			Model model) {
		try {
			AppUpgradeInfoDTO upgrade = new AppUpgradeInfoDTO();
			upgrade.setId(upgradeId);
			upgrade.setAppName(appName);
			upgrade.setUpgradeInfo(upgradeInfo);
			if (upgradeId == null) {
				CheckUtils.notEmpty(appName, "appName");
				appMgtFacade.addUpgradeInfo(upgrade);
			} else {
				appMgtFacade.updateUpgradeInfo(upgrade);
			}
			if (mail != null && mail.equals(Boolean.TRUE)) {
				BaseEventUtils.sendEvent(SoaCenterConst.SOA_APP_UPGRADE, upgrade);
			}
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除升级信息
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteUpgradeInfo(
			@RequestParam(value = "upgradeId", required = true) Long upgradeId,
			Model model) {
		try {
			appMgtFacade.deleteUpgradeInfo(upgradeId);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

}
