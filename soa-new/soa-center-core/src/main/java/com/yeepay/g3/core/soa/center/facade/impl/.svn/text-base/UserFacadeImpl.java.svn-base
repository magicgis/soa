package com.yeepay.g3.core.soa.center.facade.impl;

import com.yeepay.g3.core.soa.center.dubbo.service.UserService;
import com.yeepay.g3.facade.soa.center.dubbo.domain.User;
import com.yeepay.g3.facade.soa.center.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Title: 用户 Facade 实现类<br/>
 * Description: 描述<br/>
 * Copyright: Copyright (c)2011<br/>
 * Company: 易宝支付(YeePay)<br/><br/>
 *
 * @author baitao.ji
 * @version 0.1, 14-8-22 18:37
 */
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserService userService;

	@Override
	public List<User> findAllUsers() {
		return userService.findAllUsers();
	}

	@Override
	public User findUser(String username) {
		return userService.findUser(username);
	}

	@Override
	public User findById(Long id) {
		return userService.findById(id);
	}

	@Override
	public void createUser(User user) {
		userService.createUser(user);
	}

	@Override
	public void updateUser(User user) {
		userService.updateUser(user);
	}

	@Override
	public void modifyUser(User user) {
		userService.modifyUser(user);
	}

	@Override
	public boolean updatePassword(User user, String oldPassword) {
		return userService.updatePassword(user, oldPassword);
	}

	@Override
	public void resetPassword(User user) {
		userService.resetPassword(user);
	}

	@Override
	public void enableUser(User user) {
		userService.enableUser(user);
	}

	@Override
	public void disableUser(User user) {
		userService.disableUser(user);
	}

	@Override
	public void deleteUser(User user) {
		userService.deleteUser(user);
	}

}
