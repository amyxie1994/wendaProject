/**
 * Created by amyxie in 2018
 * EventType.java
 * 12 Mar. 2018
 */
package com.example.snsProject.async;

/**
 * @author amyxie
 *
 */
public enum EventType {
	
	LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);
	
	private int value;
	
	EventType( int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

}
