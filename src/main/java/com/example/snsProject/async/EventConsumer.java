/**
 * Created by amyxie in 2018
 * EventConsumer.java
 * 12 Mar. 2018
 */


package com.example.snsProject.async;
import com.example.snsProject.util.JedisAdapter;
import com.example.snsProject.util.RedisKeyUtil;
import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
	
	@Autowired
	JedisAdapter jedisAdapter;
	
	private Map<EventType, List<EventHandler>> config = new HashMap<EventType, List<EventHandler>>(); 
    private ApplicationContext applicationContext;

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    @Override
    public void afterPropertiesSet() throws Exception{
		Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
	    if(beans!=null)
	    {
	    		for(Map.Entry<String, EventHandler>entry:beans.entrySet()) 
	    		{
	    			List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
	    			
	    			for(EventType type: eventTypes) 
	    			{
	    				if(!config.containsKey(type))
	    				{
	    					config.put(type, new ArrayList<EventHandler>());
	    				}
	    				config.get(type).add(entry.getValue());
	    			}
	    		}
	    }
    
	    Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				String key = RedisKeyUtil.getEventQueueKey();
				List<String > events = jedisAdapter.brpop(0, key);
				for(String message:events) {
					if(message.equals(key)) {
						continue;
					}
					
					EventModel eventModel = JSON.parseObject(message,EventModel.class);
					if(!config.containsKey(eventModel.getType())) 
					{
						logger.error("request can not be identify");
					}
					
					for(EventHandler handler:config.get(eventModel.getType()) ) 
					{
						handler.doHandle(eventModel);
					}
				}
			}
			
			
		}
		
	});
	thread.start();
 }
	

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = arg0;
		
		
	}
}

