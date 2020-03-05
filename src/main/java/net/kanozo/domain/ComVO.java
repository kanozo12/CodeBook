package net.kanozo.domain;

import java.sql.Date;

public class ComVO {
	private Integer cno; // 댓글 번호
	private Integer bno; // 게시글 번호
	private String comContent; // 댓글 내용
	private String comWriter; // 댓글 작성자
	private String userName; // 댓글 작성자의 이름(회원의 이름)
	private Date regDate; // 댓글 작성일자
	private Date upDateDate; // 댓글 수정 일자

	public Integer getCno() {
		return cno;
	}

	public void setCno(Integer cno) {
		this.cno = cno;
	}

	public Integer getBno() {
		return bno;
	}

	public void setBno(Integer bno) {
		this.bno = bno;
	}

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comContent) {
		this.comContent = comContent;
	}

	public String getComWriter() {
		return comWriter;
	}

	public void setComWriter(String comWriter) {
		this.comWriter = comWriter;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpDateDate() {
		return upDateDate;
	}

	public void setUpDateDate(Date upDateDate) {
		this.upDateDate = upDateDate;
	}

	@Override
	public String toString() {
		return "ComVO [bno=" + bno + ", comContent=" + comContent + ", comWriter=" + comWriter
				+ ", userName=" + userName + "]";
	}

}
