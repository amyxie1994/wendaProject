/**
 * Created by amyxie in 2018
 * LoginController.java
 * 11 Feb. 2018
 */
package com.example.snsProject.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.snsProject.async.EventHandler;
import com.example.snsProject.async.EventModel;
import com.example.snsProject.async.EventProducer;
import com.example.snsProject.async.EventType;
import com.example.snsProject.service.UserService;

/**
 * @author amyxie
 *
 */


@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	EventProducer eventProducer;
	
	
	@Autowired
	UserService userService;
	@RequestMapping(path= {"/reglogin"},method= {RequestMethod.GET} )
	public String login(Model model,@RequestParam(value = "next",required = false)String next) {
		model.addAttribute("next",next);
		
		return "login";
	}
	
	@RequestMapping(path= {"/reg/"},method= {RequestMethod.POST} )
	public String register(Model model,@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value="rememberme", defaultValue = "false") Boolean rememberme,
			@RequestParam(value = "next",required = false) String next,
			HttpServletResponse response
			) {
	try {	
		
		Map<String, Object> map = userService.register(username,password);
		if(map.containsKey("ticket")) {
			
			Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
			
			cookie.setPath("/");
			
			if(rememberme)
				cookie.setMaxAge(3600*24*5);
			response.addCookie(cookie);
			
			
			
			if(StringUtils.isEmpty(next) )
				return "redirect:/";
			else
				return "redirect:"+next;
		}else {
			model.addAttribute("msg", map.get("msg"));
            return "login";
		}
			
		//model.addAttribute("msg",);
		
		
	}
	catch(Exception e)
	{
		logger.error("register login error"+e.getMessage());
		 model.addAttribute("msg", "服务器错误");
		return "login";
	}
	
	}
	
	
	
	@RequestMapping(path= {"/login/"},method= {RequestMethod.POST} )
	public String login(Model model,@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value="rememberme", defaultValue = "false")Boolean rememberme,
			@RequestParam(value="next", required= false) String next,
			HttpServletResponse response
			) {
	try {	
	
		Map<String, Object> map = userService.login(username,password);
		
		if(map.containsKey("ticket")) {
			
			Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
			
			cookie.setPath("/");
			
			if(rememberme)
				cookie.setMaxAge(3600*24*5);
			response.addCookie(cookie);
			
			eventProducer.fireEvent(new EventModel(EventType.LOGIN)
					.setExt("username",username).setExt("email","hongzhenx@126.com")
					.setActorId(31));
	
			if(StringUtils.isEmpty(next) )
				return "redirect:/";
			else
				return "redirect:"+next;
			
		}
		else {
			model.addAttribute("msg", map.get("msg"));
            return "login";
		}
		
	}
	catch(Exception e)
	{
		logger.error("register login error"+e.getMessage());
		return "login";
	}
	
	}
	
	
	
	@RequestMapping(path= {"/logout/"},method= {RequestMethod.GET} )
	public String logout(@CookieValue("ticket") String ticket) {
		
		userService.logout(ticket);
		return "redirect:/";
	}
	
	
	
	
	
}
