/**
 * Created by amyxie in 2018
 * MessageService.java
 * 5 Mar. 2018
 */
package com.example.snsProject.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snsProject.dao.MessageDAO;
import com.example.snsProject.model.Message;

/**
 * @author amyxie
 *
 */
@Service
public class MessageService {
	
	@Autowired
	MessageDAO messageDAO;
	
	public int addMessage(Message message)
	{
		int fromId = message.getFromId();
		int toId = message.getToId();
		
		if(fromId<toId) {
			message.setConversationId(String.format("%d_%d", fromId, toId));
		}
		else {
			message.setConversationId(String.format("%d_%d", toId, fromId));
		}
		return messageDAO.addMessage(message);
	}
	
	
	public List<Message> getMessageDetail(String conversationId,int offset,int limit){
		return messageDAO.getConversationDetail(conversationId, offset,limit);
	}
	/*
	public List<Message> getMessageList(int userId, int offset, int limit){
		
		return messageDAO.getConversationList(userId, offset, limit);
		
	}
	*/
	public int getConversationUnreadCount(int userId,String conversationId) {
		
		return messageDAO.getConvesationUnreadCount(userId, conversationId);
		
	}
	 public List<Message> getConversationList(int userId,int offset, int limit){
		 return messageDAO.getConversationList(userId, offset, limit);
	 }
	
}
