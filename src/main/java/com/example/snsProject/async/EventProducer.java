/**
 * Created by amyxie in 2018
 * EventProducer.java
 * 12 Mar. 2018
 */
package com.example.snsProject.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.alibaba.fastjson.JSONObject;
import com.example.snsProject.util.JedisAdapter;
import com.example.snsProject.util.JsonFunction;
import com.example.snsProject.util.RedisKeyUtil;

/**
 * @author amyxie
 *
 */
@Service
public class EventProducer {
	
	
	@Autowired
	JedisAdapter jedisAdapter;
	
	public boolean fireEvent(EventModel eventModel) {
		
		try {
			String json = JSONObject.toJSONString(eventModel);
			String key = RedisKeyUtil.getEventQueueKey();
			jedisAdapter.lpush(key,json);
			return true;
			
		}catch(Exception e) {
			return false;
			
		}
		
	}

}
