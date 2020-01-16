package net.kanozo.service;

import net.kanozo.domain.BoardVO;

public interface BoardService {
	//글쓰기
	public void writeArticle(BoardVO board);
}
