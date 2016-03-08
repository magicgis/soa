package com.yeepay.g3.core.soa.center.dubbo.service.impl;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.registry.RegistryService;
import com.yeepay.g3.core.soa.center.dubbo.utils.sync.RegistryServerSync;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * IbatisDAO
 *
 * @author william.liangf
 */
public class AbstractService {

	protected static final Logger logger = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	private RegistryServerSync sync;

	@Autowired
	protected RegistryService registryService;

	public ConcurrentMap<String, ConcurrentMap<String, Map<Long, URL>>> getRegistryCache() {
		return sync.getRegistryCache();
	}

}
