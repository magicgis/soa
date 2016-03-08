package com.yeepay.g3.app.soa.center.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.dto.AppInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.AppMgtFacade;
import com.yeepay.g3.facade.soa.center.facade.ConfigFacade;
import com.yeepay.g3.utils.common.DateUtils;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.soa.config.model.Config;
import com.yeepay.g3.utils.soa.config.model.ConfigDataTypeEnum;
import com.yeepay.g3.utils.soa.config.model.ConfigValueTypeEnum;
import com.yeepay.g3.utils.soa.config.model.Structure;

/**
 * 统一配置控制器
 *
 * @author：wang.bao
 * @since：2014年10月27日 下午6:30:50
 * @version:
 */
@Controller
@RequestMapping("/configs")
public class ConfigsController extends BaseController {
	@Reference
	private ConfigFacade configFacade;

	@Reference
	private AppMgtFacade appMgtFacade;

	private static final Map<ConfigDataTypeEnum, String> formats = Maps
			.newHashMap();
	static {
		formats.put(ConfigDataTypeEnum.DATE, DateUtils.DATE_FORMAT_DATEONLY);
		formats.put(ConfigDataTypeEnum.DATETIME, DateUtils.DATE_FORMAT_DATETIME);
	}

	/**
	 * 新增配置信息
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveConfig(
			@RequestParam(value = "isAdd", required = false) Boolean isAdd,
			@RequestParam(value = "application", required = true) String application,
			@RequestParam(value = "configName", required = true) String configName,
			@RequestParam(value = "configKey", required = true) String configKey,
			@RequestParam(value = "configValue", required = true) String configValue,
			@RequestParam(value = "valueType", required = true) ConfigValueTypeEnum valueType,
			@RequestParam(value = "dataType", required = false) ConfigDataTypeEnum dataType,
			@RequestParam(value = "enabled", required = true) Boolean enabled,
			Model model) {
		try {
			Config config = new Config(application, configName, configKey,
					configValue, valueType, dataType);
			config.setEnabled(enabled);
			if (isAdd != null && isAdd) {
				configFacade.addConfig(config);
			} else {
				configFacade.updateConfig(config);
			}
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 启用
	 */
	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage enableConfig(
			@RequestParam(value = "configKey", required = true) String configKey,
			@RequestParam(value = "application", required = true) String application,
			Model model) {
		try {
			configFacade.changeStatus(configKey, application, true);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 禁用
	 */
	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage disableConfig(
			@RequestParam(value = "configKey", required = true) String configKey,
			@RequestParam(value = "application", required = true) String application,
			Model model) {
		try {
			configFacade.changeStatus(configKey, application, false);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 删除配置信息
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteConfig(
			@RequestParam(value = "configKey", required = true) String configKey,
			@RequestParam(value = "application", required = true) String application,
			Model model) {
		try {
			configFacade.deleteConfig(configKey, application);
			return new ResponseMessage(null);
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	/**
	 * 配置信息详情
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{application:.+}/{configKey:.+}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage findConfig(
			@PathVariable(value = "application") String application,
			@PathVariable(value = "configKey") String configKey, Model model) {
		try {
			Config config = configFacade.findConfig(configKey, application);
			if (config != null) {
				config.setOrigUrl(null);
				// 清洗时间日期，格式化
				if (config.getValueType() == ConfigValueTypeEnum.STRUCTURE
						|| config.getDataType() == ConfigDataTypeEnum.DATE
						|| config.getDataType() == ConfigDataTypeEnum.DATETIME) {
					if (config.getValueType() == ConfigValueTypeEnum.VALUE) {
						config.setValue(formatValue(config.getValue(),
								config.getDataType()));
					} else if (config.getValueType() == ConfigValueTypeEnum.LIST) {
						List<Object> list = Lists.newArrayList();
						for (Object value : (List<?>) config.getValue()) {
							list.add(this.formatValue(value,
									config.getDataType()));
						}
						config.setValue(list);
					} else if (config.getValueType() == ConfigValueTypeEnum.MAP) {
						Map<String, Object> map = Maps.newHashMap();
						for (Entry<String, Object> kv : ((Map<String, Object>) config
								.getValue()).entrySet()) {
							map.put(kv.getKey(),
									this.formatValue(kv.getValue(),
											config.getDataType()));
						}
						config.setValue(map);
					} else if (config.getValueType() == ConfigValueTypeEnum.STRUCTURE) {
						List<Structure> list = (List<Structure>) config
								.getValue();
						Map<String, Object> map = Maps.newLinkedHashMap();
						for (Structure s : list) {
							map.put(s.getKey(),
									this.formatValue(s.getValue(), s.getType()));
						}
						config.setValue(map);
					}
				}
				return new ResponseMessage(null).put("config", config);
			} else {
				return new ResponseMessage(ResponseMessage.Status.ERROR,
						"配置项不存在");
			}
		} catch (Throwable e) {
			return new ResponseMessage(ResponseMessage.Status.ERROR,
					this.handleException(e));
		}
	}

	private Object formatValue(Object value, ConfigDataTypeEnum type) {
		if (value instanceof Date) {
			Date date = (Date) value;
			if (type == ConfigDataTypeEnum.DATE) {
				return DateUtils.getShortDateStr(date);
			} else if (type == ConfigDataTypeEnum.DATETIME) {
				return DateUtils.getTimeStampStr(date);
			}
		}
		return value;
	}

	/**
	 * 删除配置信息
	 */
	@RequestMapping(value = { "/", "/query", "/list" }, method = RequestMethod.GET)
	public String query(
			@RequestParam(value = "application", required = false) String application,
			@RequestParam(value = "configName", required = false) String configName,
			@RequestParam(value = "configKey", required = false) String configKey,
			@RequestParam(value = "configValue", required = false) String configValue,
			@RequestParam(value = "valueType", required = false) ConfigValueTypeEnum valueType,
			@RequestParam(value = "dataType", required = false) ConfigDataTypeEnum dataType,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNo",
				String.valueOf(pageNo == null || pageNo < 1 ? 1 : pageNo));
		params.put("pageSize", String
				.valueOf(pageSize == null || pageSize < 1 ? 20 : pageSize));
		if (StringUtils.isNotBlank(application)) {
			params.put("application", application);
		}
		if (StringUtils.isNotBlank(configName)) {
			params.put("_name", configName);
		}
		if (StringUtils.isNotBlank(configKey)) {
			params.put("_key", configKey);
		}
		if (StringUtils.isNotBlank(configValue)) {
			params.put("_value", configValue);
		}
		if (valueType != null) {
			params.put("_valueType", valueType.name());
		}
		if (dataType != null) {
			params.put("_dataType", dataType.name());
		}
		model.addAttribute("valueTypes", ConfigValueTypeEnum.asMap());
		model.addAttribute("dataTypes", ConfigDataTypeEnum.asMap());
		PageList result = configFacade.queryConfigs(params);
		model.addAttribute("result", result);
		List<AppInfoDTO> applications = appMgtFacade.queryAllApp(true);
		model.addAttribute("applications", applications);
		return "configs/query";
	}
}
