package net.kanozo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.kanozo.domain.BoardVO;
import net.kanozo.domain.Criteria;

@Repository
public class BoardDAOImpl implements BoardDAO {

	@Autowired
	private SqlSession session;

	private final String namespace = "net.kanozo.mappers.BoardMapper";

	@Override
	public void write(BoardVO data) {
		session.insert(namespace + ".write", data);
	}

	@Override
	public BoardVO view(Integer id) {
		return session.selectOne(namespace + ".view", id);
	}

	@Override
	public List<BoardVO> list(Criteria cri) {
		return session.selectList(namespace + ".list", cri);
	}

	@Override
	public void delete(Integer id) {
		session.delete(namespace + ".delete", id);
	}

	@Override
	public void update(BoardVO data) {
		session.update(namespace + ".update", data);
	}

	@Override
	public Integer getCnt(Criteria cri) {
		return session.selectOne(namespace + ".cnt", cri);
	}

}
