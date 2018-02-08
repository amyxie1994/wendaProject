/**
 * Created by amyxie in 2018
 * UserService.java
 * 8 Feb. 2018
 */
package com.example.snsProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snsProject.dao.UserDao;
import com.example.snsProject.model.User;

/**
 * @author amyxie
 *
 */
@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	
	
	public User getUserById(int userId)
	{
		return userDao.getUserById(userId);
	}

}
