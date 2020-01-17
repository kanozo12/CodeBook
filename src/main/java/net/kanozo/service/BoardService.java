package net.kanozo.service;

import java.util.List;

import net.kanozo.domain.BoardVO;
import net.kanozo.domain.Criteria;

public interface BoardService {
	// 글쓰기
	public void writeArticle(BoardVO board);

	// 글보기
	public BoardVO viewArticle(Integer id);

	// 글 리스트 보기
	public List<BoardVO> getArticleList(Criteria cri);

	// 글 수정하기
	public void updateArticle(BoardVO board);

	// 글 삭제하기
	public void deleteArticle(Integer id);

	// 글 갯수 가져오기
	public Integer countArticle(Criteria cri);
}