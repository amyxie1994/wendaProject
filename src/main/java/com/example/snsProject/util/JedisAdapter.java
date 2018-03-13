/**
 * Created by amyxie in 2018
 * JedisAdapter.java
 * 9 Mar. 2018
 */
package com.example.snsProject.util;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.logging.log4j2.Log4J2LoggingSystem;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.snsProject.model.User;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * @author amyxie
 *
 */

@Service

public class JedisAdapter implements InitializingBean{
	
	
	private JedisPool pool = new JedisPool();
	private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
	   

	  @Override
	 public void afterPropertiesSet() throws Exception {
	        pool = new JedisPool("redis://localhost:6379/10");
	 }
	  
	 public long sadd(String key,String value) {
		 Jedis jedis = null;
		 try{
			 jedis = pool.getResource();
			 return jedis.sadd(key, value);
		 }catch(Exception e) {
			 logger.error("exception"+e.getMessage());
			 
		 }finally {
			 if(jedis!= null)
				 jedis.close();
		 }
		 return 0;
	 }
   
	 public long scard(String key) {
		 Jedis jedis = null;
		 try{
			 jedis = pool.getResource();
			 return jedis.scard(key);
		 }catch(Exception e) {
			 logger.error("exception"+e.getMessage());
			 
		 }finally {
			 if(jedis!= null)
				 jedis.close();
		 }
		 return 0;
	 }
	 
	 public boolean sismember(String key,String value) {
		 Jedis jedis = null;
		 try{
			 jedis = pool.getResource();
			 return jedis.sismember(key,value);
		 }catch(Exception e) {
			 logger.error("exception"+e.getMessage());
			 
		 }finally {
			 if(jedis!= null)
				 jedis.close();
		 }
		 return false;
	 }
	
	 public long srem(String key, String value) {
	        Jedis jedis = null;
	        try {
	            jedis = pool.getResource();
	            return jedis.srem(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	        return 0;
	    }
	 
	public static void print(int index,Object obj) {
		System.out.println(String.format("%d %s", index,obj.toString()));
	}
	
	
	public static void main(String[] args) {
		Jedis jedis = new Jedis("redis://localhost:6379/9");
		jedis.flushDB();
		
		jedis.set("new test", "new test");
		print(1, jedis.get("new test"));
		
		jedis.rename("new test","old test");
		print(2,jedis.get("old test"));
		
		jedis.setex("hello word",15,"new test");
		
		jedis.set("pv", "100");
		jedis.incr("pv");
		jedis.incrBy("pv", 8);
		print(3,jedis.get("pv"));
		
		
		jedis.decrBy("pv",2);
		print(3,jedis.get("pv"));
		
		String listName = "list";
		
		jedis.del(listName);
		for(int i =0;i<10;i++) {
			jedis.lpush(listName, String.valueOf(i));
		}
		print(4, jedis.lrange(listName, 0, 3));
		print(4, jedis.llen(listName));
		print(4, jedis.lpop(listName));
		print(5, jedis.lindex(listName,3));
		print(6, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "5", "xx"));
		print(6, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "5", "BB"));
		print(10, jedis.lrange(listName, 0, 12));
	
		
		String userKey = "userxx";
		jedis.hset(userKey,"name","key");
		jedis.hset(userKey, "age", "12");
		jedis.hset(userKey, "phone","1234r");
		print(12, jedis.hgetAll(userKey));
		
		print(13, jedis.hget(userKey,"age"));
		print(14, jedis.hexists(userKey,"age"));
		print(14, jedis.hvals(userKey));
		jedis.hdel(userKey, "phone");
		print(14, jedis.hvals(userKey));
		
		
		String likeKey1 = "commentlike1";
		String likeKey2 = "commentLike2";
		
		for(int i=0;i<10;i++) {
			jedis.sadd(likeKey1, String.valueOf(i));
			jedis.sadd(likeKey2, String.valueOf(i*i));
		}
		
		print(20, jedis.smembers(likeKey1));
		print(21, jedis.smembers(likeKey2));
		print(22, jedis.sunion(likeKey1,likeKey2));
		print(23, jedis.sdiff(likeKey1,likeKey2));
		print(24, jedis.sinter(likeKey1,likeKey2));
		print(25, jedis.sismember(likeKey1,"12"));
		jedis.srem(likeKey1,"5");
		print(25, jedis.sismember(likeKey1,"5"));
		jedis.smove(likeKey2, likeKey1, "5");
		print(27, jedis.scard(likeKey1));
		
		String rankKey = "rankKey";
		jedis.zadd(rankKey, 15,"kim");
		jedis.zadd(rankKey, 16,"tim");
		jedis.zadd(rankKey, 17,"ben");
		
		print(30, jedis.zcard(rankKey)); //have how many people
		print(31, jedis.zcount(rankKey,15 ,17));
		print(30, jedis.zscore(rankKey,"tim"));
		
		jedis.zincrby(rankKey, 2, "kim");
		print(30, jedis.zscore(rankKey,"kim")); //对不存在的就默认原始为0 
		print(32, jedis.zrange(rankKey, 0, 100)); //从小到大排序 
	
		for(Tuple tuple: jedis.zrangeByScoreWithScores(rankKey, "10", "20")) {
			print(37, tuple.getElement()+":"+String.valueOf(tuple.getScore()));
		}
		
		print(38, jedis.zrank(rankKey, "tim")); //paiming
		print(38, jedis.zrevrank(rankKey, "tim")); //paiming
		
		String setKey = "zset";
		jedis.zadd(setKey, 1,"a");
		jedis.zadd(setKey, 1,"b");
		jedis.zadd(setKey, 1,"c");
		jedis.zadd(setKey, 1,"d");
		jedis.zadd(setKey, 1,"e");
		
		print(40, jedis.zlexcount(setKey, "-", "+")); //-infinite , +infinite
		print(40, jedis.zlexcount(setKey, "[b", "[d")); //from b to d,if ( means do not contain
		print(44, jedis.zremrangeByLex(setKey, "(c", "+"));
		
		jedis.set("pv", "100");
		//JedisPool  pool = new JedisPool();
		/*for(int i = 0;i<100;i++) {
			Jedis j = pool.getResource();
			
			print(45, j.get("pv"));
			j.close();
		}
	*/
		
		User user = new User();
		user.setName("xx");
		user.setPassword("ddd");
		user.setSalt("dssd");
		print(46, JSONObject.toJSONString(user));
		jedis.set("user1", JSONObject.toJSONString(user));
		
		String value = jedis.get("user1");
		User user2 = JSON.parseObject(value,User.class);  //json xuliehua 
		print(47, user2);
		
	
	}
	
	 public long lpush(String key, String value) {
	        Jedis jedis = null;
	        try {
	            jedis = pool.getResource();
	            return jedis.lpush(key, value);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	        } finally {
	            if (jedis != null) {
	                jedis.close();
	            }
	        }
	        return 0;
	    }
	 
	 public List<String> brpop(int timeout, String key) {
	        Jedis jedis = null;
	        try {
	            jedis = pool.getResource();
	            return jedis.brpop(timeout, key);
	        } catch (Exception e) {
	            logger.error("发生异常" + e.getMessage());
	        } finally {
	            if (jedis != null) {
	                jedis.close(); 
	            }
	        }
	        return null;
	    }
}
