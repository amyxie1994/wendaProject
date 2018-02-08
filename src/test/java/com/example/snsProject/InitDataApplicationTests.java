package com.example.snsProject;

import java.util.Date;
import java.util.Random;

import javax.swing.JMenu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.snsProject.dao.QuestionDAO;
import com.example.snsProject.dao.UserDao;
import com.example.snsProject.model.Question;
import com.example.snsProject.model.User;

@RunWith(SpringRunner.class)

@SpringBootTest
public class InitDataApplicationTests {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	QuestionDAO questionDao;
	@Test
	public void initDatabases() {
		//String name ="amy";
		Random random = new Random();
		for (int j = 0;j<10;j++)
		{
			User user = new User();
			user.setName(String.format("user%d",j));
			user.setPassword(String.format("password:%d", j));
			user.setSalt(String.format("salt is %d", j));
			
			user.setUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
			userDao.addUser(user);
			
		}
		
		
		for(int i =0 ;i<10;i++)
		{
			Question question = new Question();
			question.setCommentCount(i);
			Date date = new Date();
			date.setTime(date.getTime()+1000*3600*i);
			question.setCreatedDate(date);
			question.setContent(String.format("content is %d", i));
			question.setTitle(String.format("title is %d",i ));
			question.setUserId(i+10);
			
			questionDao.addQuestion(question);
		}
		System.out.println(questionDao.selectLatestQuestion(0,0,10));
		
		
		
		
	}

}
