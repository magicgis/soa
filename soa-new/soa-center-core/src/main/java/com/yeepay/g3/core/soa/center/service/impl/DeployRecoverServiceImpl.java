package com.yeepay.g3.core.soa.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yeepay.g3.core.soa.center.dao.DeployRecordDao;
import com.yeepay.g3.core.soa.center.entity.DeployRecord;
import com.yeepay.g3.core.soa.center.service.DeployRecoverService;
import com.yeepay.g3.core.soa.center.utils.DataConvertUtils;
import com.yeepay.g3.facade.soa.center.dto.DeployRecordDTO;

/**
 *
 * @author：wang.bao
 * @since：2015年12月9日 下午5:37:59
 * @version:
 */
public class DeployRecoverServiceImpl implements DeployRecoverService {
	@Autowired
	private DeployRecordDao deployRecordDao;

	@Override
	public Long deploy(DeployRecordDTO record) {
		DeployRecord entity = DataConvertUtils.convert(record,
				DeployRecord.class);
		deployRecordDao.add(entity);
		return entity.getId();
	}

	@Override
	public DeployRecordDTO findById(Long id) {
		return DataConvertUtils.convert(deployRecordDao.get(id),
				DeployRecordDTO.class);
	}

	@Override
	public List<Long> findAllUnRecovered() {
		return deployRecordDao.findAllUnRecovered();
	}

	@Override
	public void recover(Long id) {
		deployRecordDao.recover(id);
	}

}
