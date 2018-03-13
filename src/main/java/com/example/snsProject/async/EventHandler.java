/**
 * Created by amyxie in 2018
 * EventHandler.java
 * 12 Mar. 2018
 */
package com.example.snsProject.async;

import java.util.List;

/**
 * @author amyxie
 *
 */
public interface EventHandler {
	void doHandle(EventModel model);
	
	List<EventType> getSupportEventTypes();

}
