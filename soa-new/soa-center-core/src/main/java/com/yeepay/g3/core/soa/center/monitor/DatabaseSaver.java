/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.dao.MonitorDataDao;
import com.yeepay.g3.core.soa.center.entity.MonitorData;

/**
 * @author：wang.bao
 * @since：2015年8月18日 上午9:11:57
 * @version:
 */
@Component
public class DatabaseSaver extends AbstractMonitorDataSaver {
	@Autowired
	private MonitorDataDao monitorDataDao;

	@Override
	public String getName() {
		return "database";
	}

	@Override
	protected void doSave(MonitorData data) throws Exception {
		this.monitorDataDao.insert(data);
	}

	@Override
	protected void doInit() {
	}

	@Override
	protected void doDestory() {
	}
}
