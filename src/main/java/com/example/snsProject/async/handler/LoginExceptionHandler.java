/**
 * Created by amyxie in 2018
 * LoginExceptionHandler.java
 * 13 Mar. 2018
 */
package com.example.snsProject.async.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.snsProject.async.EventHandler;
import com.example.snsProject.async.EventModel;
import com.example.snsProject.async.EventType;
import com.example.snsProject.util.MailSender;

/**
 * @author amyxie
 *
 */
@Component
public class LoginExceptionHandler implements EventHandler {

	/* (non-Javadoc)
	 * @see com.example.snsProject.async.EventHandler#doHandle(com.example.snsProject.async.EventModel)
	 */
	@Autowired
	MailSender mailSender;
	
	@Override
	public void doHandle(EventModel model) {
		// TODO Auto-generated method stub
		Map<String , Object> map = new HashMap<String,Object>();
		map.put("username", model.getExt("username"));
		mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆IP异常", "mails/mainTemplate.ftl", map);
		//mailSender.sendWithH
	}

	/* (non-Javadoc)
	 * @see com.example.snsProject.async.EventHandler#getSupportEventTypes()
	 */
	@Override
	public List<EventType> getSupportEventTypes() {
		// TODO Auto-generated method stub
		return Arrays.asList(EventType.LOGIN) ;
	}

}
