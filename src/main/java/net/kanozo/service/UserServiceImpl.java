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

	@Override
	public void fillLevelTable(Integer max) {
		userDao.deleteLevelTable(); // 레벨 데이터 삭제하고

		for (int i = 0; i <= max; i++) {
			Integer exp = (int) Math.floor(Math.pow(((double) i - 1) * 50 / 49, 2.5) * 10);
			userDao.insertLevelData(i, exp);
		}
	}

	@Override
	public UserVO appExp(String userid, Integer exp) {
		UserVO user = userDao.getUser(userid);
		user.setExp(user.getExp() + exp);
		Integer requireExp = userDao.getRequireExp(user.getLevel() + 1);

		if (user.getExp() >= requireExp) {
			user.setExp(user.getExp() - requireExp);
			user.setLevel(user.getLevel() + 1);
		}

		// 경험치 증가 처리후 DB에 저장
		userDao.setLevelAndExp(user);
		
		return user;
	}
	
	
}
