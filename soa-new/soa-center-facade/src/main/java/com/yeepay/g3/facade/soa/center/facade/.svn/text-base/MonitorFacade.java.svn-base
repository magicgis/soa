package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dto.MonitorChartDataDTOWarpper;
import com.yeepay.g3.facade.soa.center.dto.MonitorDataQueryVO;
import com.yeepay.g3.facade.soa.center.dto.MonitorStatisticsDataDTOWarpper;

import java.text.ParseException;
import java.util.Map;

/**
 * Title: <br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 16:04
 */
public interface MonitorFacade {

	/**
	 * 查询统计数据
	 *
	 * @param queryVO 需要传递startDate、service
	 * @return
	 */
	Map<String, MonitorStatisticsDataDTOWarpper> lookupStatisticsData(MonitorDataQueryVO queryVO);

	/**
	 * 查询chart数据
	 *
	 * @param queryVO 需要传递startDate、service
	 * @return
	 */
	Map<String, MonitorChartDataDTOWarpper> lookupChartData(MonitorDataQueryVO queryVO) throws ParseException;

}
