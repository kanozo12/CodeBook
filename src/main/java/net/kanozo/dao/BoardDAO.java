package net.kanozo.dao;

import net.kanozo.domain.BoardVO;

public interface BoardDAO {
	//글을 쓰는 메서드
	public void write(BoardVO data);
}


