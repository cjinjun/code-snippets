package com.code.codesnippets.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

@Configuration
public class RedisPool {

    private final static Logger logger = LoggerFactory.getLogger(RedisPool.class);


   private static JedisPool jedisPool = null;

   
   
   /**
    * 初始化redis
    * @Title:			intRedisPool 
    * @Description:	TODO
    * @param:			    
    * @return:			void    
    * @throws: 
    * @author:			admin
    * @Date:			2016年11月1日 下午4:13:20
    */
   @PostConstruct
   public synchronized void  initialize(){
	   if(jedisPool == null){
		   try {
			   JedisPoolConfig config = new JedisPoolConfig();
			   config.setMaxWaitMillis(2000);
			   config.setMaxTotal(500);
			   config.setMaxIdle(30);
			   config.setMinIdle(10);
			   config.setTestOnReturn(true);
			   config.setTestOnBorrow(true);

			   String redisIP =  "127.0.0.1";
			   //设置各类参数   暂不设置
               RedisPool.logger.info("connect to redis  {}", redisIP);
			   jedisPool = new JedisPool(config, redisIP, 6379, 2000, "123", 1);
			} catch (Exception e) {
		       RedisPool.logger.error("connect to redis erro: {}", e);
			}
	   }
   }


   public JedisPool getJedisPool(){
   		if( jedisPool ==null ){
   			initialize();
		}
		return jedisPool;
   }


   /**
    * 获取redis实例
    * @Title:			getJedis 
    * @Description:	TODO
    * @param:			@return    
    * @return:			Jedis    
    * @throws: 
    * @author:			admin
    * @Date:			2016年11月1日 下午4:16:14
    */
   public  static Jedis getJedis(){
	   Jedis resource = null;
	   if(resource == null){
		    resource = jedisPool.getResource();
	   }
	   return resource;
   }


   public static int getNumActive(){
   		return jedisPool.getNumActive();

   }

   /**
    * 释放连接
    * @Title:			releaseRedis 
    * @Description:	TODO
    * @param:			@param jedis    
    * @return:			void    
    * @throws: 
    * @author:			admin
    * @Date:			2016年11月1日 下午4:18:58
    */
   public static void releaseRedis(final Jedis jedis){
	   if (jedis != null) {
		   jedisPool.returnResource(jedis);
	   }
   }

   public static void closeJedis(final  Jedis jedis){
   		if(jedis.isConnected()){
   			try{
   				jedis.disconnect();
			}catch (Exception e){
   				logger.info("disconnect jedis error:{}", e.getStackTrace());
			}
		}
   		jedis.close();
   }


   public  static String get(String key){
   		Jedis jedis = null;
		try	{
			jedis = RedisPool.getJedis();
			return jedis.get(key);
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

   public static void main(String[] args) {
	
	   getJedis();
	   
   }
   
}
