/**
 * Created by amyxie in 2018
 * QuestionService.java
 * 8 Feb. 2018
 */
package com.example.snsProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<Question> selectLastestQuestion(int userId, int offset, int limit)
	{
		return questionDao.selectLatestQuestion(userId, offset, limit);
		
	}
	

}
