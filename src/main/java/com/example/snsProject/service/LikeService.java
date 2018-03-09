/**
 * Created by amyxie in 2018
 * LikeService.java
 * 10 Mar. 2018
 */
package com.example.snsProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.snsProject.util.JedisAdapter;
import com.example.snsProject.util.RedisKeyUtil;

/**
 * @author amyxie
 *
 */
@Service
public class LikeService {
	
	@Autowired
	JedisAdapter jedisAdapter;
	
	public long getLikeCount(int entityType,int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		return jedisAdapter.scard(likeKey);
		
	}
	
	public long like(int userId,int entityType,int entityId) {
		String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		jedisAdapter.sadd(likeKey, String.valueOf(userId));
		
		
		String disLikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
		jedisAdapter.srem(disLikeKey, String.valueOf(userId));
		
		
		
		
		return jedisAdapter.scard(likeKey);
	}
	
	
	public int getLikeStatus(int userId,int entityType, int entityId) {
		
		String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		if(jedisAdapter.sismember(likeKey, String.valueOf(userId)))
			return 1;
		
		String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
		if(jedisAdapter.sismember(dislikeKey, String.valueOf(userId)))
			return -1;
		return 0;
	}
	
	public long disLike(int userId,int entityType,int entityId) {
		
		String disLikeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
		jedisAdapter.sadd(disLikeKey, String.valueOf(userId));		
		
		String likeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
		jedisAdapter.srem(likeKey, String.valueOf(userId));
		
		return jedisAdapter.scard(likeKey);
	}

}
