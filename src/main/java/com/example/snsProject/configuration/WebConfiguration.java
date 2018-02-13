/**
 * Created by amyxie in 2018
 * WebConfiguration.java
 * 12 Feb. 2018
 */
package com.example.snsProject.configuration;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.example.snsProject.interceptor.LoginInterceptor;
import com.example.snsProject.interceptor.PassportInterceptor;

/**
 * @author amyxie
 *
 */
@Component
public class WebConfiguration extends WebMvcConfigurerAdapter{
	
	@Autowired
    PassportInterceptor passportInterceptor;
	
	@Autowired
	LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(passportInterceptor);
		registry.addInterceptor(loginInterceptor).addPathPatterns("/user/*");
		super.addInterceptors(registry);
		
	}
	
	

}
