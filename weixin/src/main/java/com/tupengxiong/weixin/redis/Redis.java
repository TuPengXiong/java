package com.tupengxiong.weixin.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * ClassName: Redis <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年6月14日 下午1:31:47 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 */
public class Redis {

	private JedisPool pool;
	private String ip = "127.0.0.1";
	private int port = 6379;

	public Jedis getJedisInPool() {
		return this.pool.getResource();
	}

	public Redis() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 设置最大实例总数
		jedisPoolConfig.setMaxTotal(150);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setMinIdle(10);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		jedisPoolConfig.setMaxWaitMillis(3 * 1000);
		// 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
		jedisPoolConfig.setTestOnBorrow(true);
		// 在还会给pool时，是否提前进行validate操作
		jedisPoolConfig.setTestOnReturn(true);
		jedisPoolConfig.setTestWhileIdle(true);
		jedisPoolConfig.setMinEvictableIdleTimeMillis(500);
		jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1000);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(1000);
		jedisPoolConfig.setNumTestsPerEvictionRun(100);
		pool = new JedisPool(jedisPoolConfig, ip, port, 5000, null, 0);
	}

	public Redis(String ip, int port) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 设置最大实例总数
		jedisPoolConfig.setMaxTotal(150);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		jedisPoolConfig.setMaxIdle(30);
		jedisPoolConfig.setMinIdle(10);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		jedisPoolConfig.setMaxWaitMillis(3 * 1000);
		// 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
		jedisPoolConfig.setTestOnBorrow(true);
		// 在还会给pool时，是否提前进行validate操作
		jedisPoolConfig.setTestOnReturn(true);
		jedisPoolConfig.setTestWhileIdle(true);
		jedisPoolConfig.setMinEvictableIdleTimeMillis(500);
		jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1000);
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(1000);
		jedisPoolConfig.setNumTestsPerEvictionRun(100);
		pool = new JedisPool(jedisPoolConfig, ip, port, 5000, null, 0);
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
