package com.yeepay.g3.app.soa.center.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.facade.RegistrySyncFacade;

/**
 *
 * @author：wang.bao
 * @since：2014年8月28日 下午3:56:11
 * @version:
 */
@Controller
@RequestMapping("/registry")
public class RegistryController extends BaseController {
	@Reference
	private RegistrySyncFacade registrySyncFacade;

	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage syncRegistry(Model model) {
		try {
			registrySyncFacade.syncRegistry();
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	@RequestMapping(value = "/clearCache", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage clearCache(Model model) {
		try {
			registrySyncFacade.clearCache();
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	@RequestMapping(value = "/unregister", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage unregister(
			@RequestParam(value = "id", required = true) Long id, Model model) {
		try {
			registrySyncFacade.unregister(id);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	@RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage unsubscribe(
			@RequestParam(value = "id", required = true) Long id, Model model) {
		try {
			registrySyncFacade.unsubscribe(id);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}
}
