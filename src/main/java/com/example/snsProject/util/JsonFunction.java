/**
 * Created by amyxie in 2018
 * JsonFunction.java
 * 21 Feb. 2018
 */
package com.example.snsProject.util;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * @author amyxie
 *
 */
public class JsonFunction {

	 public static int ANONYMOUS_USERID = 11;
	 public static int SYSTEM_USERID = 4;
	 
	// private static final Logger logger = LoggerFactory.getLogger(EncryptAlgorithm.class);
	 
	 public static String  getJsonString(int code) {
		 
		 JSONObject json = new JSONObject();
	     json.put("code", code);
	     return json.toJSONString();
		 
	 }
	 
	 public static String getJsonString(int code, String msg)
	 {
		 JSONObject json = new JSONObject();
	     json.put("code", code);
	     json.put("msg", msg);
	     return json.toJSONString();
	 }
}
