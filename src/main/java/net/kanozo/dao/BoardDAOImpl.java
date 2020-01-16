package net.kanozo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import net.kanozo.domain.BoardVO;

public class BoardDAOImpl implements BoardDAO {
	
	@Autowired
	private SqlSession session;
	
	private final String namespace = "net.kanozo.mappers.BoardMapper";
	
	@Override
	public void write(BoardVO data) {
		session.insert(namespace + ".write", data); 
	}
}
