package net.kanozo.dao;

import net.kanozo.domain.UserVO;

public interface UserDAO {
	public UserVO getUser(String userid);
	public UserVO loginUser(String userid, String password);
	public void insertUser(UserVO user);
}
