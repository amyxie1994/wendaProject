/**
 * Created by amyxie in 2018
 * LogAspect.java
 * 1 Feb. 2018
 */
package com.example.snsProject.aspect;




import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;




/**
 * @author amyxie
 *
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	
	@Before("execution(* com.example.snsProject.controller.IndexController.*(..))")
	public void beforeMethod()
	{
		logger.info("before method");
	}
	
}
