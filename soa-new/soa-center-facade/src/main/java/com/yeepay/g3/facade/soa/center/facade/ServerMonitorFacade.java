package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dto.ServerInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.StatusInfoDTO;
import com.yeepay.g3.facade.soa.center.dto.SystemInfoDTO;
import com.yeepay.g3.facade.soa.center.param.ClientQueryParam;
import com.yeepay.g3.utils.common.page.PageList;

import java.util.List;

/**
 * @author by menghao.chen
 */
public interface ServerMonitorFacade {
	/**
	 * 获取操作系统信息
	 *
	 * @return
	 */
	SystemInfoDTO querySystemInfo();

	/**
	 * 获取服务器列表信息
	 *
	 * @return
	 */
	List<ServerInfoDTO> queryServerInfos();

	/**
	 * 查询运行时状态
	 *
	 * @return
	 */
	List<StatusInfoDTO> queryRuntimeStatus();

	/**
	 * 查询客户列表
	 *
	 * @param clientQueryParam
	 * @return
	 */
	PageList queryClient(ClientQueryParam clientQueryParam);
}
