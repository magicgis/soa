package com.yeepay.g3.facade.soa.center.facade;

import com.yeepay.g3.facade.soa.center.dubbo.domain.User;

import java.util.List;

/**
 * Title: 用户 Facade<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:35
 */
public interface UserFacade {

	List<User> findAllUsers();

	User findUser(String username);

	User findById(Long id);

	void createUser(User user);

	void updateUser(User user);

	void modifyUser(User user);

	boolean updatePassword(User user, String oldPassword);

	void resetPassword(User user);

	void enableUser(User user);

	void disableUser(User user);

	void deleteUser(User user);

}
