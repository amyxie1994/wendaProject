/**
 * Created by amyxie in 2018
 * PassportInterceptor.java
 * 12 Feb. 2018
 */
package com.example.snsProject.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.snsProject.dao.TicketDAO;
import com.example.snsProject.dao.UserDao;
import com.example.snsProject.model.HostHolder;
import com.example.snsProject.model.Ticket;
import com.example.snsProject.model.User;

/**
 * @author amyxie
 *
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{
	
	@Autowired
	HostHolder hostHolder;
	
	@Autowired
	TicketDAO ticketDAO;
	
	@Autowired
	UserDao userDao;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	
	hostHolder.clear();
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {

	 if (modelAndView != null && hostHolder.getUser() != null) {
         modelAndView.addObject("user", hostHolder.getUser());
        
     }
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object cObject) throws Exception {
		String ticket = null;
		if( request.getCookies()!= null) {
			for(Cookie cookie:request.getCookies()) {
				if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
				}
			}
	
		}
		if(ticket!=null)
		{
			Ticket loginTicket = ticketDAO.getTicketByTicket(ticket);
			
			
			if (loginTicket == null || loginTicket.getExpiredDate().before(new Date()) || loginTicket.getStatus() != 0) {
				
	            return true;
	        }
			
			User newUser = userDao.getUserById(loginTicket.getUserId());	
			hostHolder.setUser(newUser);
			
		}
	
	
		// TODO Auto-generated method stub
		return true;
	}

	
	
	
	

}
