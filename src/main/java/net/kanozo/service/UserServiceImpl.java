package net.kanozo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kanozo.dao.UserDAO;
import net.kanozo.domain.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public UserVO getUserInfo(String userid) {
		return userDao.getUser(userid);
	}

	@Override
	public UserVO login(String userid, String password) {
		return userDao.loginUser(userid, password);
	}

	@Override
	public void register(UserVO user) {
		userDao.insertUser(user);
	}
}
