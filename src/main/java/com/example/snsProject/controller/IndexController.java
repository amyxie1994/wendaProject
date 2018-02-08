/**
 * Created by amyxie in 2018
 * IndexController.java
 * 28 Jan. 2018
 */
package com.example.snsProject.controller;





import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * Created by amyxie in 2018
 * IndexController.java
 * 28 Jan. 2018
 */


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.snsProject.service.WendaService;


/**
 * @author amyxie
 *
 */
//@Controller
public class IndexController {
	
	
	@Autowired
	WendaService wendaService;
	
	@RequestMapping(path= {"/","/index"},method= {RequestMethod.GET} )
	@ResponseBody
	public String index(HttpSession htsession) {
		return "hello world"+wendaService.wenda()+htsession.getAttribute("msg");
		
	}
	
	
	@RequestMapping(path= {"/request"},method= {RequestMethod.GET} )
	@ResponseBody
	public String request(Model model,HttpServletResponse response,
			HttpServletRequest request ) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getPathInfo());
		
		response.addCookie(new Cookie("loggusr", "amyxie"));
		return sb.toString();
		
		
		
	}
	
	//302 temporary redirect
	@RequestMapping(path= {"/redirect2/{code}"},method= {RequestMethod.GET} )
	public String redirect(@PathVariable("code") int code,HttpSession htsession ) {
		htsession.setAttribute("msg", "jump from redirect");
		return "redirect:/";
		
		
		
	}
	
	
	
	//301 permanent redirect
		@RequestMapping(path= {"/redirect/{code}"},method= {RequestMethod.GET} )
		public RedirectView redirect2(@PathVariable("code") int code,HttpSession htsession ) {
			htsession.setAttribute("msg", "jump from redirect");
			
			RedirectView rView = new RedirectView("/",true); //redirect location 
			if(code ==301)
				rView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);// page status
			else {
				rView.setStatusCode(HttpStatus.FOUND);
			}
			
			return rView;
			
			
			
		}
		
	//pass variable
	@RequestMapping(path= {"/profile/{groupid}/{userid}"})
	@ResponseBody
	public String profile(@PathVariable("userid") int userId,
						 @PathVariable("groupid") String groupId,
						 @RequestParam("type") int type,
						 @RequestParam(value = "key", defaultValue="zzz",required= false) String key)
						 {
		return String.format("User id is %d groupid is : %s\n type is: %d, key is %s", userId,groupId,type,key);
						 }
		
	@RequestMapping(path= {"/vm"})
	public ModelAndView template(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("message","nihao");
		mav.setViewName("helloworld");
		return mav;
	}
	
	@RequestMapping(path= {"/vm2"})
	public String template2(Model model){
		
		User2 user = new User2("amy xie");
		model.addAttribute("ser",user.getName());
		System.out.println(user.getName());
		model.addAttribute("value1","vvv1");
		String[] colcor = {"red","blue"};
		model.addAttribute("colors",colcor);
		return "home2" ;
	}
	
	@RequestMapping(path= {"/vm44"})
	public String template(Model model, HttpServletResponse response,
			HttpServletRequest request,
			HttpSession httpSession){
		StringBuilder sb = new StringBuilder();
		sb.append(request.getMethod());
		
		return sb.toString();
	}
	
	
	
	@RequestMapping(path= {"/admin"},method= {RequestMethod.GET} )
	@ResponseBody
	public String admin(@RequestParam("key") String key) {
		if ("admin".equals(key))
			return "hello admin";
		throw new  IllegalArgumentException("argument not correct");
		
	}
	
	
	
	//exception handler
		@ExceptionHandler()
		@ResponseBody
		public String error(Exception e ) {
			return "error"+e.getMessage();
			
			
			
		}
	
/*	
	@RequestMapping(path= {"/vm3"})
	public String template22(Model model){
		model.addAttribute("value1","vvv1");
		String[] colcor = {"red","blue"};
		model.addAttribute("colors",colcor);
		return "home22" ;
	}
*/
}


class User2{
	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	public User2(String name) {
		this.name = name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
}


