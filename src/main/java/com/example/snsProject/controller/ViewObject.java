/**
 * Created by amyxie in 2018
 * ViewObject.java
 * 8 Feb. 2018
 */
package com.example.snsProject.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * @author amyxie
 *
 */
public class ViewObject {
	
	private Map<String,Object> objs = new HashMap<String,Object>();
	
	/**
	 * @return the objs
	 */
	public Map<String, Object> getObjs() {
		return objs;
	}

	/**
	 * @param objs the objs to set
	 */
	public void setObjs(Map<String, Object> objs) {
		this.objs = objs;
	}

	public void set(String key,Object value) {
		objs.put(key, value);
	}

	public Object get(String key) {
		return objs.get(key);
	}
}
