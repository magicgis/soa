package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.Access;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Title: 访问控制 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-21 10:45
 */
public interface AccessFacade {

	/**
	 * 查询指定服务的访问规则
	 *
	 * @param service 服务
	 * @return
	 */
	List<Access> findByService(String service) throws ParseException;

	/**
	 * 查询指定机器的访问规则
	 *
	 * @param address 机器
	 * @return
	 */
	List<Access> findByAddress(String address) throws ParseException;

	/**
	 * 查询所有访问规则
	 *
	 * @return
	 */
	List<Access> findAll() throws ParseException;

	/**
	 * 新建路由规则
	 *
	 * @param service  服务名
	 * @param address  服务地址
	 * @param allow    是否允许
	 * @param operator 操作员
	 */
	void create(String service, String address, boolean allow, String operator) throws IOException, ParseException;

	/**
	 * 根据服务名和服务地址删除路由
	 *
	 * @param service 服务名
	 * @param address 服务地址
	 */
	void deleteByServiceAndAddress(List<String> service, List<String> address, String operator) throws ParseException;

}
