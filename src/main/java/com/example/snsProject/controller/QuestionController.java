/**
 * Created by amyxie in 2018
 * QuestionController.java
 * 21 Feb. 2018
 */
package com.example.snsProject.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.snsProject.model.Comment;
import com.example.snsProject.model.EntityType;
import com.example.snsProject.model.HostHolder;
import com.example.snsProject.model.Question;
import com.example.snsProject.service.CommentService;
import com.example.snsProject.service.LikeService;
import com.example.snsProject.service.QuestionService;
import com.example.snsProject.service.UserService;
import com.example.snsProject.util.JsonFunction;

/**
 * @author amyxie
 *
 */
@Controller
public class QuestionController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	@Autowired
	QuestionService questionService;
	
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(path= {"/question/{qid}"})
	public String questionDetail(@PathVariable("qid") int qid, Model model) {
		Question question = questionService.selectQuestionById(qid);
		model.addAttribute("question",question);
		
		List<Comment> commentList  = commentService.selectByEntity(qid, EntityType.ENTITY_QUESTION);
		List<ViewObject> vos = new ArrayList<>();
	        for (Comment comment : commentList) {
	            ViewObject vo = new ViewObject();
	            vo.set("comment", comment);
	            if(hostHolder.getUser() == null)
	            	    vo.set("liked", 0);
	            else {
	                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
	            }
	            
	            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_QUESTION,comment.getId()));
	            vo.set("user", userService.getUserById(comment.getUserId()));
	            vos.add(vo);
	        }
	    model.addAttribute("comments", vos);
	        
		model.addAttribute("user",hostHolder.getUser());
		return "detail";
		
	}
	
	@RequestMapping(path= {"/question/add"},method= {RequestMethod.POST} )
	@ResponseBody
	public String addQuestion(@RequestParam("title") String title,
			@RequestParam("content") String content) {
	try {	
		Question question = new Question();
		question.setTitle(title);
		question.setContent(content);
		question.setCreatedDate(new Date());
		
		if(hostHolder.getUser() == null) {
			question.setUserId(JsonFunction.ANONYMOUS_USERID);
		}
		else {
			question.setUserId(hostHolder.getUser().getId());
		}
	
		if(questionService.addQuestion(question)>0)
			return JsonFunction.getJsonString(0);
	
	}
	catch(Exception e)
	{
		logger.error("add question fail"+e.getMessage());
	}
	return JsonFunction.getJsonString(1,"add question fail");
	}

}
