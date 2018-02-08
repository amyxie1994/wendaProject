/**
 * Created by amyxie in 2018
 * Question.java
 * 7 Feb. 2018
 */
package com.example.snsProject.model;

import java.util.Date;

/**
 * @author amyxie
 *
 */
public class Question {
	private int id;
	private String title;
	private String content;
	private Date createdDate;
	private int userId;
	private int commentCount;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the createDdATE
	 */
	public Date getCreateDdATE() {
		return createdDate;
	}
	/**
	 * @param createDdATE the createDdATE to set
	 */
	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}
	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	
	

}
