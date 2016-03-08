/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao.impl;

import java.util.List;

import com.yeepay.g3.core.soa.center.dao.DeployRecordDao;
import com.yeepay.g3.core.soa.center.entity.DeployRecord;
import com.yeepay.g3.utils.persistence.mybatis.GenericDaoDefault;

/**
 *
 * @author：wang.bao
 * @since：2015年12月9日 下午4:13:13
 * @version:
 */
public class DeployRecordDaoImpl extends GenericDaoDefault<DeployRecord>
		implements DeployRecordDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllUnRecovered() {
		return this.query("findAllUnRecovered");
	}

	@Override
	public void recover(Long id) {
		this.update("recover", id);
	}
}
