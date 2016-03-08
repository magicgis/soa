package com.yeepay.g3.app.soa.center.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.app.soa.center.utils.ViewLogicHelper;
import com.yeepay.g3.facade.soa.center.dto.ServerInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.StatusInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.ServerMonitorFacade;
import com.yeepay.g3.facade.soa.center.param.ClientQueryParam;
import com.yeepay.g3.utils.common.CollectionUtils;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * @author by menghao.chen
 */
@Controller
@RequestMapping("/monitor/servers")
public class ServersMonitorController extends BaseController {
	@Reference
	private ServerMonitorFacade serverMonitorFacade;

	@Autowired
	private ViewLogicHelper viewLogicHelper;

	@RequestMapping(value = "")
	public String loadInfo(Model model) {
		List<ServerInfoDTO> serverInfoList = serverMonitorFacade
				.queryServerInfos();
		List<StatusInfoDTO> statusInfoList = serverMonitorFacade
				.queryRuntimeStatus();
		Integer clientCount = 0;
		if (CollectionUtils.isNotEmpty(serverInfoList)) {
			for (ServerInfoDTO serverInfo : serverInfoList) {
				clientCount += serverInfo.getClientSize();
			}
		}
		model.addAttribute("servers", serverInfoList);
		model.addAttribute("clientCount", clientCount);
		model.addAttribute("statusList", statusInfoList);
		model.addAttribute("system", serverMonitorFacade.querySystemInfo());
		model.addAttribute("viewLogicHelper", viewLogicHelper);
		return "monitor/servers";
	}

	@RequestMapping("/clients")
	public String loadClients(
			@RequestParam("port") Integer port,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			Model model) {
		ClientQueryParam queryParam = new ClientQueryParam();
		queryParam.setAddress(address);
		queryParam.setPort(port);
		queryParam.setPageNo(pageNo);
		queryParam.setPageSize(pageSize);
		PageList pageList = serverMonitorFacade.queryClient(queryParam);
		model.addAttribute("result", pageList);
		return "monitor/clients";
	}
}
