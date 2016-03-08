package com.yeepay.g3.core.soa.center.dao;

import com.yeepay.g3.core.soa.center.entity.MonitorChartData;
import com.yeepay.g3.core.soa.center.entity.MonitorData;
import com.yeepay.g3.facade.soa.center.dto.MonitorDataQueryVO;
import com.yeepay.g3.facade.soa.center.dto.MonitorStatisticsDataDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Title: 监控数据 Respository<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-9-11 10:31
 */
@Repository
public interface MonitorDataDao {

	Integer insert(MonitorData monitorData);

	List<MonitorStatisticsDataDTO> lookupStatisticsData(@Param("queryVO") MonitorDataQueryVO queryVO);

	List<MonitorChartData> lookupChartData(@Param("queryVO") MonitorDataQueryVO queryVO);

}
