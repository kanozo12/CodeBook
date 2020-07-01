package net.kanozo.dao;

import java.util.List;

import net.kanozo.domain.BoardVO;
import net.kanozo.domain.Criteria;

public interface BoardDAO {
	// 글을 쓰는 메서드
	public void write(BoardVO data);

	// 글보기 메서드
	public BoardVO view(Integer id);

	// 글 리스트 보기(몇번부터 몇개를 볼 것인지)
	public List<BoardVO> list(Criteria cri);

	// 글 삭제
	public void delete(Integer id);

	// 글 수정
	public void update(BoardVO data);

	// 현재 글의 갯수
	public Integer getCnt(Criteria cri);

	// 글을 쓰는 메서드
	public void write2(BoardVO data);

	// 글보기 메서드
	public BoardVO view2(Integer id);
	
	// 글 수정
	public void update2(BoardVO data);

	// 글 리스트 보기(몇번부터 몇개를 볼 것인지)
	public List<BoardVO> list2(Criteria cri);

	// 현재 글의 갯수
	public Integer getCnt2(Criteria cri);
}
