package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dto.ServiceInfoDTO;
import com.yeepay.g3.facade.soa.center.param.ServiceQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

/**
 * 服务查询接口
 *
 * @author：menghao.chen wang.bao
 * @since：2014年7月3日 上午11:15:06
 * @version:
 */
public interface ServiceQueryFacade {
	/**
	 * 服务查询
	 *
	 * 根据查询类型进行查询，查询类型为空时，优先应用查询，其次服务查询
	 *
	 * @param queryParam
	 *            服务查询参数
	 * @return
	 */
	PageList queryService(ServiceQueryParam queryParam);

	ServiceInfoDTO findService(String serviceInterface);
}
