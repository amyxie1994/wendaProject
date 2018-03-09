/**
 * Created by amyxie in 2018
 * QuestionService.java
 * 8 Feb. 2018
 */
package com.example.snsProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.example.snsProject.dao.QuestionDAO;
import com.example.snsProject.model.Question;

/**
 * @author amyxie
 *
 */
@Service
public class QuestionService {
	@Autowired
	QuestionDAO questionDao;
	
	@Autowired 
	SensitiveWordService sensitiveWordService;
	
	public List<Question> selectLastestQuestion(int userId, int offset, int limit)
	{
		return questionDao.selectLatestQuestion(userId, offset, limit);
		
	}
	
	
	public Question selectQuestionById(int id) {
		return questionDao.selectById(id);
	}
	
	public int addQuestion(Question question) {
		
		question.setContent(HtmlUtils.htmlEscape(question.getContent()));
		question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
		 question.setTitle(sensitiveWordService.filter(question.getTitle()));
	        question.setContent(sensitiveWordService.filter(question.getContent()));
		return questionDao.addQuestion(question)>0?question.getId():0;
	}
	
	
	
	public void updateCommentCount(int id, int count) {
		questionDao.updateCommentCount(id,count);
	}
	


}
