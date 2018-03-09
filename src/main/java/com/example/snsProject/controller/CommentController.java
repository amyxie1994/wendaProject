/**
 * Created by amyxie in 2018
 * CommentController.java
 * 3 Mar. 2018
 */
package com.example.snsProject.controller;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import com.example.snsProject.model.Comment;
import com.example.snsProject.model.EntityType;
import com.example.snsProject.model.HostHolder;
import com.example.snsProject.service.CommentService;
import com.example.snsProject.service.QuestionService;
import com.example.snsProject.service.SensitiveWordService;
import com.example.snsProject.util.JsonFunction;

/**
 * @author amyxie
 *
 */
@Controller
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	@Autowired
	SensitiveWordService sensitiveWordService;
	
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	CommentService commentService;
	
	
	@Autowired
    QuestionService questionService;
	
	
	@RequestMapping(path= {"/addComment"},method= {RequestMethod.POST} )
	public String addComment(@RequestParam("content") String content,@RequestParam("questionId") int questionId) {
		
		try {
			
			Comment comment = new Comment();
			
			content= HtmlUtils.htmlEscape(content);
			content= sensitiveWordService.filter(content);
			
			if(hostHolder.getUser() == null)
				comment.setUserId(JsonFunction.ANONYMOUS_USERID);
			else
				comment.setUserId(hostHolder.getUser().getId());
			
			comment.setContent(content);
			comment.setCreatedDate(new Date());
			comment.setEntityId(questionId);
			comment.setStatus(0);
			comment.setEntityType(EntityType.ENTITY_QUESTION);
			
			commentService.addComment(comment);
			
			int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
			
		}catch(Exception e) {
			logger.error("add comment fails"+e.getMessage());
		}
		
		return "redirect:/question/"+String.valueOf(questionId);
	}

}
