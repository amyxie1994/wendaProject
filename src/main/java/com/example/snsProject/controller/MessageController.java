/**
 * Created by amyxie in 2018
 * MessageController.java
 * 6 Mar. 2018
 */
package com.example.snsProject.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.View;

import org.hibernate.validator.internal.metadata.aggregated.ValidatableParametersMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.util.HtmlUtils;
import org.yaml.snakeyaml.reader.UnicodeReader;

import com.example.snsProject.model.HostHolder;
import com.example.snsProject.model.Message;
import com.example.snsProject.model.User;
import com.example.snsProject.service.MessageService;
import com.example.snsProject.service.QuestionService;
import com.example.snsProject.service.SensitiveWordService;
import com.example.snsProject.service.UserService;
import com.example.snsProject.util.JsonFunction;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

/**
 * @author amyxie
 *
 */
@Controller
public class MessageController {
	
	private Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	SensitiveWordService sensitiveWordService;
	
	@RequestMapping(path = "/msg/addMessage" ,method = {RequestMethod.POST})
	@ResponseBody 
	public String addMessage(@RequestParam("toName") String toName,@RequestParam ("content") String content) {
		
		try {
			if(hostHolder.getUser() == null) {
				return JsonFunction.getJsonString(999,"NOT LOG IN YET");
			}
			else {
				User user = userService.selectByName(toName); 
				if (user == null) {
	                return JsonFunction.getJsonString(1, "用户不存在");
	            }
				
				Message message = new Message();
				content = HtmlUtils.htmlEscape(content);
				message.setContent(sensitiveWordService.filter(content));
				message.setFromId(hostHolder.getUser().getId());
				message.setToId(user.getId());
				message.setCreatedDate(new Date());
				
				messageService.addMessage(message);

				return JsonFunction.getJsonString(0);
				
			}
		}catch (Exception e) {
			logger.error("send message fials"+e.getMessage());
			return JsonFunction.getJsonString(1, "send fails");
			
		}
		
	}
	
    @RequestMapping(path=("/msg/list"),method = {RequestMethod.GET})
    public String getMessageList(Model model){
    	
    	try {
    		if(hostHolder.getUser()==null)
    			return "redirect:/reglogin";
    		
    		int userId = hostHolder.getUser().getId();
    		List<ViewObject> vos = new ArrayList<ViewObject> ();
    		List<Message> messages = new ArrayList<Message>();
    		messages = messageService.getConversationList(userId, 0, 10);
    		
    		for(Message message : messages) {
    			ViewObject vo = new ViewObject();
    			vo.set("conversation", message);
    			User user = userService.getUserById(message.getFromId());
    			vo.set("url", user.getUrl());
    			vo.set("user", user);
    			vo.set("unread", messageService.getConversationUnreadCount(userId, message.getConversationId()));
    			vos.add(vo);
    		}
    		
    		model.addAttribute("conversations",vos);
    		
    		
    	}catch(Exception e) {
    		logger.error("get detail fails"+e.getMessage());
    	}
    	return "letter";
    }
    
    @RequestMapping(path=("/msg/detail"),method = {RequestMethod.GET})
    public String getMessageDetail(Model model, @RequestParam("conversationId") String conversationId){
    	
    		try {
    			List <Message> messageList = messageService.getMessageDetail(conversationId, 0, 10);
    			List<ViewObject> vos = new ArrayList<ViewObject>();
    		
    			
    			for(Message message : messageList) {
    				ViewObject vo = new ViewObject();
    				vo.set("message",message);
    				User user = userService.getUserById(message.getFromId());
    				if(user== null)
    					continue;
    				vo.set("user", user.getName());
    				vo.set("url", user.getUrl());
    				vo.set("userId", user.getId());
    				vos.add(vo);
    			}
    			model.addAttribute("messages",vos);
    			
    		}catch(Exception e) {
    			logger.error("get letter detail fails"+e.getMessage());
    			
    		}
    		
    		return "letterDetail";
    }
	
	
	
}
