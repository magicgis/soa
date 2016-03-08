package com.yeepay.g3.core.soa.center.dubbo.service.impl;

import com.yeepay.g3.core.soa.center.dubbo.service.UserService;
import com.yeepay.g3.facade.soa.center.dubbo.domain.User;
import com.yeepay.g3.facade.soa.center.utils.Coder;

import java.util.List;
import java.util.Map;

/**
 * IBatisUserService
 *
 * @author william.liangf
 */
public class UserServiceImpl extends AbstractService implements UserService {

	private String rootPassword;

	public void setRootPassword(String password) {
		this.rootPassword = (password == null ? "" : password);
	}

	private String guestPassword;

	public void setGuestPassword(String password) {
		this.guestPassword = (password == null ? "" : password);
	}

	public User findUser(String username) {
		if ("guest".equals(username)) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(Coder.encodeMd5(username + ":" + User.REALM + ":" + guestPassword));
			user.setName(username);
			user.setRole(User.GUEST);
			user.setEnabled(true);
			user.setLocale("zh");
			user.setServicePrivilege("");
			return user;
		} else if ("root".equals(username)) {
			User user = new User();
			user.setUsername(username);
			user.setPassword(Coder.encodeMd5(username + ":" + User.REALM + ":" + rootPassword));
			user.setName(username);
			user.setRole(User.ROOT);
			user.setEnabled(true);
			user.setLocale("zh");
			user.setServicePrivilege("*");
			return user;
		}
		return null;
	}

	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, User> findAllUsersMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public User findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createUser(User user) {
		// TODO Auto-generated method stub

	}

	public void updateUser(User user) {
		// TODO Auto-generated method stub

	}

	public void modifyUser(User user) {
		// TODO Auto-generated method stub

	}

	public boolean updatePassword(User user, String oldPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	public void resetPassword(User user) {
		// TODO Auto-generated method stub

	}

	public void enableUser(User user) {
		// TODO Auto-generated method stub

	}

	public void disableUser(User user) {
		// TODO Auto-generated method stub

	}

	public void deleteUser(User user) {
		// TODO Auto-generated method stub

	}

	public List<User> findUsersByServiceName(String serviceName) {
		// TODO Auto-generated method stub
		return null;
	}

}
