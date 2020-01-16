package net.kanozo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kanozo.domain.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/**/root-context.xml" })
public class UserDAOTest {

	@Autowired
	private UserDAO dao;

	@Test
	public void testInsertUser() throws Exception {
		UserVO user = new UserVO();
		user.setUserid("kanozo123");
		user.setPassword("1234");
		user.setImg("");
		user.setName("유준우");
		dao.insertUser(user);
		System.out.println(".....insert..ok..");
	}

	@Test
	public void testSelectUser() throws Exception {
		UserVO user = dao.getUser("kanozo12");
		System.out.println(user);
		System.out.println(".....select..ok..");
	}

	@Test
	public void testLogin() throws Exception {
		UserVO user = dao.loginUser("kanozo12", "1234");
		System.out.println(user);
		System.out.println(".....login..ok..");
	}
}
