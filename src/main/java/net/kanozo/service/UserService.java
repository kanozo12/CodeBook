package net.kanozo.service;

import net.kanozo.domain.UserVO;

public interface UserService {
	public UserVO login(String usrid, String password);
	
	public void register(UserVO user);
	
	public UserVO getUserInfo(String userid);
}
