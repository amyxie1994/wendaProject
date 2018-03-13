/**
 * Created by amyxie in 2018
 * LikeHandler.java
 * 13 Mar. 2018
 */
package com.example.snsProject.async.handler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.snsProject.async.EventHandler;
import com.example.snsProject.async.EventModel;
import com.example.snsProject.async.EventType;
import com.example.snsProject.model.Message;
import com.example.snsProject.model.User;
import com.example.snsProject.service.MessageService;
import com.example.snsProject.service.UserService;
import com.example.snsProject.util.JsonFunction;

/**
 * @author amyxie
 *
 */
@Component
public class LikeHandler implements EventHandler{
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;

	
	@Override
	public void doHandle(EventModel model) {
		// TODO Auto-generated method stub
		Message message = new Message();
		message.setFromId(JsonFunction.SYSTEM_USERID);
		message.setToId(model.getEntityOwnerId());
		message.setCreatedDate(new Date());
		User user = userService.getUserById(model.getActorId());
		message.setContent("用户" + user.getName()
        + "赞了你的评论,http://127.0.0.1:8080/question/" 
				+ model.getExt("questionId"));
		messageService.addMessage(message);
	}

	
	@Override
	public List<EventType> getSupportEventTypes() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LIKE);
	}
	
	
	

}
