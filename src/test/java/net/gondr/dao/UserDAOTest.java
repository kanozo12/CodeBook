package net.gondr.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.gondr.domain.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/**/root-context.xml" })
public class UserDAOTest {
	@Autowired
	private UserDAO dao;

//	@Test
	public void testInsertUser() throws Exception {
		UserVO user = new UserVO();
		user.setUserid("gondr99");
		user.setPassword("1234");
		user.setImg("");
		user.setName("최선한");
		dao.insertUser(user);
	}

	@Test
	public void testSelectUser() throws Exception {
		UserVO user = dao.getUser("gondr99");
		System.out.println(user);
	}

	//@Test
	public void testLogin() throws Exception {
		UserVO user = dao.loginUser("gondr99", "1234");
		System.out.println(user);
	}
}
