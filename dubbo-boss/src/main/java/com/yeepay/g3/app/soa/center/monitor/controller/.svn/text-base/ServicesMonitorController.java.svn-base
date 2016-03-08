package com.yeepay.g3.app.soa.center.monitor.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yeepay.g3.app.soa.center.controller.BaseController;
import com.yeepay.g3.facade.soa.center.dto.MonitorDataQueryVO;
import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.facade.MonitorFacade;
import com.yeepay.g3.facade.soa.center.facade.ProviderFacade;
import com.yeepay.g3.facade.soa.center.facade.ServiceMgtFacade;
import com.yeepay.g3.utils.ajaxquery.interceptor.ControllerContext;
import com.yeepay.g3.utils.common.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Title: 服务监控<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-5 11:03
 */
@Controller
@RequestMapping("/monitor/services")
public class ServicesMonitorController extends BaseController {

    @Reference
    private ServiceMgtFacade serviceMgtFacade;

    @Reference
    private MonitorFacade monitorFacade;

    @Reference
    private ProviderFacade providerFacade;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String data(Model model,
                       HttpServletRequest request) {
        model.addAttribute("services", providerFacade.findServices());

        String dateStr = StringUtils.replace(request.getParameter("dateStr"), "-", "");
        if (StringUtils.length(dateStr) == 8) {
            ControllerContext.getContext().put("dateStr", DateUtils.getReqDate());
        }
        return "monitor/data";
    }

    @RequestMapping(value = {"/list", "/query"}, method = RequestMethod.GET)
    public String query(Model model) {
        List<ServiceInfoDTO> services = serviceMgtFacade.queryAll(true);
        model.addAttribute("services", services);
        return "monitor/services";
    }

    @RequestMapping(value = {"", "/statistics"}, method = RequestMethod.GET)
    public String statistics(@RequestParam(value = "service", required = false) String service,
                             @RequestParam(value = "date", required = false) String date,
                             @RequestParam(value = "endDate", required = false) String endDate,
                             @RequestParam(value = "expand", required = false) String expand,
                             Model model) {
        if (date == null || date.length() == 0) {
            date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        if (endDate == null || endDate.length() == 0) {
            endDate = date;
        }

        MonitorDataQueryVO queryVO = new MonitorDataQueryVO();
        queryVO.setStartDate(date);
        queryVO.setEndDate(endDate);
        queryVO.setService(service);
        queryVO.setType(expand);
        model.addAttribute("service", service);
        model.addAttribute("expand", expand);
        model.addAttribute("statisticsData", monitorFacade.lookupStatisticsData(queryVO));
        return "monitor/statistics";
    }

    @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public String charts(@RequestParam(value = "service") String service,
                         @RequestParam(value = "date", required = false) String date,
                         @RequestParam(value = "endDate", required = false) String endDate,
                         Model model) throws ParseException {
        if (service == null || service.length() == 0) {
            throw new IllegalArgumentException("必须指定服务名称");
        }

        if (date == null || date.length() == 0) {
            date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        }
        if (endDate == null || endDate.length() == 0) {
            endDate = date;
        }

        MonitorDataQueryVO queryVO = new MonitorDataQueryVO();
        queryVO.setStartDate(date);
        queryVO.setEndDate(endDate);
        queryVO.setService(service);

        model.addAttribute("service", service);
        model.addAttribute("chartData", monitorFacade.lookupChartData(queryVO));
        return "monitor/charts";
    }

//	@ResponseBody
//	@RequestMapping(value = "/lookup", method = RequestMethod.GET)
//	public ResponseMessage lookup(@RequestParam(value = "service") String service,
//								  @RequestParam(value = "date", required = false) String date,
//								  @RequestParam(value = "endDate", required = false) String endDate) throws ParseException {
//		if (service == null || service.length() == 0) {
//			throw new IllegalArgumentException("必须指定服务名称");
//		}
//
//		if (date == null || date.length() == 0) {
//			date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		}
//		if (endDate == null || endDate.length() == 0) {
//			endDate = date;
//		}
//
//		MonitorDataQueryVO queryVO = new MonitorDataQueryVO();
//		queryVO.setStartDate(date);
//		queryVO.setEndDate(endDate);
//		queryVO.setService(service);
//		return new ResponseMessage(monitorFacade.lookupChartData(queryVO));
//	}

}
