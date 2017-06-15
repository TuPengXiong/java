package com.tupengxiong.redis_session.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * ClassName: RedisSingleton <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年6月14日 下午1:31:47 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 */
public class RedisSingleton extends JedisPoolConfig {

	private JedisPool pool;
	private String ip = "127.0.0.1";
	private int port = 6379;
	private static RedisSingleton redisSingleton = new RedisSingleton();

	public static synchronized Jedis getJedisInPool() {
		return redisSingleton.getPool().getResource();
	}

	/**
	 * getPool:获取连接池配置. <br/>
	 * 
	 * @author tupengxiong
	 * @return
	 * @since JDK 1.7
	 */
	private JedisPool getPool() {
		if (pool == null) {
			// 初始化连接池
			pool = new JedisPool(this, ip, port);
		}
		return pool;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

}
