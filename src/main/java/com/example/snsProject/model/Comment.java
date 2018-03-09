/**
 * Created by amyxie in 2018
 * Comment.java
 * 3 Mar. 2018
 */
package com.example.snsProject.model;

import java.util.Date;

/**
 * @author amyxie
 *
 */
public class Comment {
	
	private int userId;
	private int entityId;
	private int entityType;
	private String content;
	private Date createdDate;
	private int id;
	private int Status;
	
	

	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return Status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		Status = status;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getEntityId() {
		return entityId;
	}
	
	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	

}
