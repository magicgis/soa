package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dto.TagInfoDTO;

/**
 * 服务标签接口
 *
 * @author：menghao.chen
 * @since：2014年7月3日 下午2:38:13
 * @version:
 */
public interface ServiceTagFacade {
	/**
	 * 新增服务标签
	 *
	 * @param tagInfoDTO
	 */
	void addServiceTag(TagInfoDTO tagInfoDTO);

	/**
	 * 根据标签Id删除标签
	 *
	 * @param tagId
	 */
	void deleteById(Long tagId);

	/**
	 * 根据标签类型，标签名称和关联的服务或应用的id删除标签
	 *
	 * @param tagInfoDTO
	 */
	void deleteServiceTag(TagInfoDTO tagInfoDTO);

}
