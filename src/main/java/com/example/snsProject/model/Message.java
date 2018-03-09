/**
 * Created by amyxie in 2018
 * Message.java
 * 5 Mar. 2018
 */
package com.example.snsProject.model;

import java.util.Date;

/**
 * @author amyxie
 *
 */
public class Message {

	
	private int id;
	private int fromId;
	private int toId;
	private String content;
	private Date createdDate;
	private int hasRead;
	private String conversationId;
	
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
	 * @return the fromId
	 */
	public int getFromId() {
		return fromId;
	}
	/**
	 * @param fromId the fromId to set
	 */
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	/**
	 * @return the toId
	 */
	public int getToId() {
		return toId;
	}
	/**
	 * @param toId the toId to set
	 */
	public void setToId(int toId) {
		this.toId = toId;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the hasRead
	 */
	public int getHasRead() {
		return hasRead;
	}
	/**
	 * @param hasRead the hasRead to set
	 */
	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}
	/**
	 * @return the conversationId
	 */
	public String getConversationId() {
		return conversationId;
	}
	/**
	 * @param conversationId the conversationId to set
	 */
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	
	
	
}
