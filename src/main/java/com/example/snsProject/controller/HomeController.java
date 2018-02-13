/**
 * Created by amyxie in 2018
 * HomeController.java
 * 7 Feb. 2018
 */
package com.example.snsProject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.snsProject.aspect.LogAspect;

import com.example.snsProject.model.Question;
import com.example.snsProject.service.QuestionService;
import com.example.snsProject.service.UserService;


/**
 * @author amyxie
 *
 */
@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	QuestionService questionService;
	
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@RequestMapping(path= {"/","/index"},method= {RequestMethod.GET} )
	public String index(Model model) {
		
		logger.info("index home page");
		List<Question> questionList = questionService.selectLastestQuestion(0,0,10);
		List<ViewObject> vos = new ArrayList<ViewObject>();
		
		for(Question question:questionList) {
			ViewObject vo = new ViewObject();
			vo.set("question", question);
			vo.set("user",userService.getUserById(question.getUserId()));
			vos.add(vo);
		}
		
		model.addAttribute("vos",vos);
		System.out.println("model is "+model.toString());
		return "index";
		
	}
	
	@RequestMapping(path= {"/user/{userId}"})
	public String userIndex(Model model,@PathVariable("userId") int userId) {
		List<Question> questionList = questionService.selectLastestQuestion(userId,0,10);
		List<ViewObject> vos = new ArrayList<ViewObject>();
		
		for(Question question:questionList) {
			ViewObject vo = new ViewObject();
			vo.set("question", question);
			vo.set("user",userService.getUserById(userId));
			vos.add(vo);
		}
		
		model.addAttribute("vos",vos);
		return "index";
	}
	
	

	
	

}
