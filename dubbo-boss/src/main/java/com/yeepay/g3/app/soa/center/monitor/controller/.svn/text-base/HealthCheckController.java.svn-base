package com.yeepay.g3.app.soa.center.monitor.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.app.soa.center.dto.ResponseMessage;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.param.AddressQueryParam;
import com.yeepay.g3.utils.common.StringUtils;
import com.yeepay.g3.utils.common.ValidateUtils;
import com.yeepay.g3.utils.common.json.JSONUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * 应用健康状态检查
 *
 * @author：wang.bao
 * @since：2015年9月7日 下午1:54:11
 * @version:
 */
@Controller
@RequestMapping("/monitor")
public class HealthCheckController extends BaseController {
	@Reference
	private AddressFacade addressFacade;

	/**
	 * 机器查询
	 */
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public String query(
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "appName", required = false) String appName,
			@RequestParam(value = "side", required = false, defaultValue = Constants.PROVIDER) String side,
			Model model) {
		AddressQueryParam queryParam = new AddressQueryParam();
		queryParam.setPageNo(1);
		queryParam.setPageSize(2000);
		if (StringUtils.equalsIgnoreCase(side, Constants.PROVIDER)
				|| StringUtils.equalsIgnoreCase(side, Constants.CONSUMER)) {
			queryParam.setSide(side);
		}
		queryParam.setAddress(address);
		queryParam.setAppName(appName);
		PageList result = addressFacade.queryAddress(queryParam);
		model.addAttribute("result", result);
		model.addAttribute("side", side);
		return "monitor/health";
	}

	@RequestMapping(value = "/health/{address}/{appName}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage check(
			@PathVariable(value = "address") String address,
			@PathVariable(value = "appName") String appName, Model model) {
		ResponseMessage response = new ResponseMessage(
				ResponseMessage.Status.SUCCESS, null);
		try {
			String ip = address;
			String port = "8088";
			int p = address.indexOf(":");
			if (p > 0) {
				ip = address.substring(0, p);
				port = address.substring(p + 1);
			}
			if (port.equals("20880")) {
				port = "8088";
			}
			if (ValidateUtils.isIP(ip)) {
				String url = "http://" + ip + ":" + port + "/" + appName
						+ "/soa/health/status";
				Map<String, Object> data = doCheck(url);
				response.getData().putAll(data);
			}
		} catch (Throwable e) {
			response.setStatus(ResponseMessage.Status.ERROR);
			response.getData().putAll(errorSummary(this.handleException(e)));
		}
		return response;
	}

	private Map<String, Object> errorSummary(String message) {
		Map<String, Object> summary = new HashMap<String, Object>();
		Map<String, Object> detail = new HashMap<String, Object>();
		detail.put("level", "ERROR");
		detail.put("message", message);
		summary.put("summary", detail);
		return summary;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> doCheck(String url) {
		HttpClient client = new DefaultHttpClient();
		try {
			// 设置编码
			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			// 设置请求超时
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
			// 设置读取超时
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					8000);
			HttpResponse response = client.execute(new HttpGet(url));

			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()
					|| HttpStatus.SC_SERVICE_UNAVAILABLE == response
							.getStatusLine().getStatusCode()) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String responseText = EntityUtils.toString(entity).trim();
					return JSONUtils
							.jsonToMap(responseText, String.class, null);
				}
			}
			return errorSummary("HttpStatusCode : "
					+ response.getStatusLine().getStatusCode());
		} catch (Exception e) {
			return errorSummary(this.handleException(e));
		} finally {
			client.getConnectionManager().shutdown();
		}
	}
}
