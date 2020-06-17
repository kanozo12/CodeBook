package net.kanozo.domain;

import java.sql.Date;

public class BoardVO {
	private Integer id;
	private String title;
	private String content;
	private String writer;
	private Date writeDate;

	private String name;
	private String img;
	private Integer u_level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getU_level() {
		return u_level;
	}

	public void setU_level(Integer u_level) {
		this.u_level = u_level;
	}

	@Override
	public String toString() {
		return "BoardVO [id=" + id + ", title=" + title + ", content=" + content + ", writer=" + writer + ", writeDate="
				+ writeDate + ", name=" + name + ", img=" + img + ", u_level=" + u_level + ", getId()=" + getId()
				+ ", getTitle()=" + getTitle() + ", getContent()=" + getContent() + ", getWriter()=" + getWriter()
				+ ", getWriteDate()=" + getWriteDate() + ", getName()=" + getName() + ", getImg()=" + getImg()
				+ ", getU_level()=" + getU_level() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
