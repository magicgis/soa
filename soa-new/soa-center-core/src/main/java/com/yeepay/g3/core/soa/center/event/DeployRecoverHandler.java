package com.yeepay.g3.core.soa.center.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yeepay.g3.core.soa.center.service.DeployRecoverService;
import com.yeepay.g3.core.soa.center.utils.SoaCenterConst;
import com.yeepay.g3.facade.soa.center.dto.AddressDTO;
import com.yeepay.g3.facade.soa.center.dto.DeployRecordDTO;
import com.yeepay.g3.facade.soa.center.enums.SoaStatusEnum;
import com.yeepay.g3.facade.soa.center.facade.AddressFacade;
import com.yeepay.g3.facade.soa.center.param.AddressQueryParam;
import com.yeepay.g3.utils.common.exception.YeepayRuntimeException;
import com.yeepay.g3.utils.common.log.Logger;
import com.yeepay.g3.utils.common.log.LoggerFactory;
import com.yeepay.g3.utils.common.page.PageList;
import com.yeepay.g3.utils.event.ext.BaseEventListener;

/**
 * 恢复服务
 *
 * @author：wang.bao
 * @since：2015年12月9日 下午4:34:41
 * @version:
 */
@Component
public class DeployRecoverHandler extends BaseEventListener {
	private final static Logger logger = LoggerFactory
			.getLogger(DeployRecoverHandler.class);

	@Autowired
	private DeployRecoverService deployRecoverService;

	@Autowired
	private AddressFacade addressFacade;

	@Override
	public void doAction(Object... args) {
		if (args == null || args.length < 1) {
			logger.info("参数不足");
			return;
		}
		Long id = (Long) args[0];
		DeployRecordDTO record = deployRecoverService.findById(id);
		if (record != null && record.getRecoverTime() == null) {
			String address = record.getAddress();
			addressFacade.enable(address, true);
			try {
				Thread.sleep(2000l);
			} catch (InterruptedException e) {
			}
			// 启用后检查是否确实正常启用，未正常启用则自动重试
			AddressQueryParam queryParam = new AddressQueryParam();
			queryParam.setAddress(address);
			queryParam.setWithCache(false);
			PageList list = addressFacade.queryAddress(queryParam);
			if (list.getData() != null && !list.getData().isEmpty()) {
				for (Object obj : list.getData()) {
					AddressDTO addr = (AddressDTO) obj;
					if (addr.getStatus() != SoaStatusEnum.ACTIVE) {
						throw new YeepayRuntimeException(
								"应用未正常启用,IP：{0}，应用名：{1}", addr.getAddress(),
								addr.getAppList());
					}
				}
			}
			// 标记为已恢复
			deployRecoverService.recover(id);
		}
	}

	@Override
	public String getListenedEventName() {
		return SoaCenterConst.DEPLOY_RECOVER;
	}
}
