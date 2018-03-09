/**
 * Created by amyxie in 2018
 * RedisKeyUtil.java
 * 10 Mar. 2018
 */
package com.example.snsProject.util;

/**
 * @author amyxie
 *
 */
public class RedisKeyUtil {
	
	private static String SPLIT = ":";
	private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";
    
    public static String getLikeKey(int entityType,int entityId) {
    	    return BIZ_LIKE+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
    
    public static String getDislikeKey(int entityType,int entityId) {
	    return BIZ_DISLIKE+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
}

}
