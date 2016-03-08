/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.yeepay.g3.core.soa.center.dao;

import java.util.List;

import com.yeepay.g3.core.soa.center.entity.DeployRecord;
import com.yeepay.g3.utils.persistence.GenericDao;

/**
 *
 * @author：wang.bao
 * @since：2015年12月9日 下午4:12:17
 * @version:
 */
public interface DeployRecordDao extends GenericDao<DeployRecord> {

	List<Long> findAllUnRecovered();

	void recover(Long id);
}
