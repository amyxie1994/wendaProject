/**
 * Created by amyxie in 2018
 * UserService.java
 * 8 Feb. 2018
 */
package com.example.snsProject.service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snsProject.dao.TicketDAO;
import com.example.snsProject.dao.UserDao;
import com.example.snsProject.model.Ticket;
import com.example.snsProject.model.User;
import com.example.snsProject.util.EncryptAlgorithm;
import com.mysql.jdbc.StringUtils;



/**
 * @author amyxie
 *
 */
@Service
public class UserService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	TicketDAO ticketDAO;
	
	
	
	public User getUserById(int userId)
	{
		return userDao.getUserById(userId);
	}
	

	public Map<String,Object> login(String username,String password) 
	{
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtils.isEmptyOrWhitespaceOnly(username)) {
			map.put("msg", "username can not be empty.");
			return map;
		}
		
		if(StringUtils.isEmptyOrWhitespaceOnly(password)) {
			map.put("msg", "password can not be empty.");
			return map;
		}
		
		User user = userDao.getUserByName(username);
		if(user==null) {
			map.put("msg", "username do not exist.");
			return map;
		}
		
		if(!user.getPassword().equals(EncryptAlgorithm.MD5(password+user.getSalt()))){
		
			map.put("msg", "password not correct.");
			return map;
		}		
				
		
        Ticket ticket = new Ticket();
        ticket.setUserId(user.getId());
        ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        Date expiredDate = new Date();
        expiredDate.setTime( expiredDate.getTime()+3600*24*1000);
        ticket.setExpiredDate(expiredDate);
        ticket.setStatus(0);
        ticketDAO.addTicket(ticket);
        
        map.put("ticket", ticket.getTicket());
        
		return map;
	}
	
	
	public Map<String,Object> register(String username,String password) 
	{
		Map<String, Object> map = new HashMap<String,Object>();
		if(StringUtils.isEmptyOrWhitespaceOnly(username)) {
			map.put("msg", "username can not be empty.");
			return map;
		}
		
		if(StringUtils.isEmptyOrWhitespaceOnly(password)) {
			map.put("msg", "password can not be empty.");
			return map;
		}
		
		User user = userDao.getUserByName(username);
		if(user!=null) {
			map.put("msg", "username has already been register");
		}
		
		user = new User();
		user.setName(username);
		
		String salt= UUID.randomUUID().toString().substring(0, 5);
		user.setSalt(salt);
		String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setUrl(head);
		
        user.setPassword(EncryptAlgorithm.MD5(password+salt));   
        userDao.addUser(user);
		
        Ticket ticket = new Ticket();
        ticket.setUserId(userDao.getUserByName(username).getId());
        ticket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        Date expiredDate = new Date();
        expiredDate.setTime( expiredDate.getTime()+3600*24*1000);
        ticket.setExpiredDate(expiredDate);
        ticket.setStatus(0);
        ticketDAO.addTicket(ticket);
        
        map.put("ticket", ticket.getTicket());
        
		return map;
	}
	
	public void logout(String ticket) {
		ticketDAO.updateStatus(ticket,1);
	}

}
