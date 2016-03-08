/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.log.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yeepay.g3.core.soa.log.service.BizLogService;
import com.yeepay.g3.utils.common.CheckUtils;
import com.yeepay.g3.utils.soa.log.BizLogDTO;
import com.yeepay.g3.utils.soa.service.SoaBizLogService;

/**
 * @author：wang.bao
 * @since：2014年11月5日 下午4:17:54
 * @version:
 */
@Service
public class SoaBizLogFacadeImpl implements SoaBizLogService {
	@Autowired
	private BizLogService bizLogService;

	@Override
	public void save(BizLogDTO bizLog) {
		CheckUtils.notNull(bizLog, "bizLog");
		CheckUtils.notEmpty(bizLog.getLoggerName(), "loggerName");
		CheckUtils.notEmpty(bizLog.getLogContent(), "logContent");
		if (bizLog.isLogTable()) {
			CheckUtils.notEmpty(bizLog.getColumns(), "columns");
		}
		bizLogService.save(bizLog);
	}

	@Override
	public void batchSave(List<BizLogDTO> bizLogs) {
		CheckUtils.notEmpty(bizLogs, "bizLogs");
		bizLogService.save(bizLogs);
	}
}
