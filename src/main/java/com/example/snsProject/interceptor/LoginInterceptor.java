/**
 * Created by amyxie in 2018
 * LoginInterceptor.java
 * 14 Feb. 2018
 */
package com.example.snsProject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.snsProject.model.HostHolder;

/**
 * @author amyxie
 *
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	HostHolder hostHolder;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object o)throws Exception {
		
		if(hostHolder.getUser()==null) {
		
			response.sendRedirect("/reglogin?next="+request.getRequestURI());
			return false;
		}
		
		return true;
		
	}
	
	

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
	

}
