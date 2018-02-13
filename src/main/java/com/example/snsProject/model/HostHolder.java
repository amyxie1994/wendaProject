/**
 * Created by amyxie in 2018
 * HostHolder.java
 * 12 Feb. 2018
 */
package com.example.snsProject.model;


import org.springframework.stereotype.Component;

/**
 * @author amyxie
 *
 */
@Component
public class HostHolder {
	
	private static ThreadLocal<User> users = new ThreadLocal<User>();

	/**
	 * @return the users
	 */
	public User getUser() {
		return users.get();
	}

	/**
	 * @param user the users to set
	 */
	public void setUser(User user) {
		users.set(user); 
	}
	
	
	public void clear() {
		users.remove();
	}

}
