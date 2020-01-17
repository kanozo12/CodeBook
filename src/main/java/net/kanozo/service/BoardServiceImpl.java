package net.kanozo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.kanozo.dao.BoardDAO;
import net.kanozo.domain.BoardVO;
import net.kanozo.domain.Criteria;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO dao;

	@Override
	public void writeArticle(BoardVO board) {
		dao.write(board);
	}

	public BoardVO viewArticle(Integer id) {
		return dao.view(id);
	}

	public List<BoardVO> getArticleList(Criteria cri) {
		return dao.list(cri);
	}

	public void updateArticle(BoardVO board) {
		dao.update(board);
	}

	public void deleteArticle(Integer id) {
		dao.delete(id);
	}

	public Integer countArticle(Criteria cri) {
		return dao.getCnt(cri);
	}
}
