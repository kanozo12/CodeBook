package net.kanozo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.kanozo.domain.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/**/root-context.xml"})
public class BoardDAOTest {
	@Autowired
	private BoardDAO dao;
	
	@Test 
	public void createBoard() {
		BoardVO board = new BoardVO();
		board.setContent("글내용입니다, 테스트 내용");
		board.setTitle("글 제목입니다, 테스트용");
		board.setWriter("kanozo");
		
		dao.write(board);
	}
}
