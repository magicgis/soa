package com.yeepay.g3.core.soa.center.dubbo.service;

import com.yeepay.g3.facade.soa.center.dubbo.domain.User;

import java.util.List;

/**
 * UserService
 *
 * @author william.liangf
 */
public interface UserService {

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
