package com.tupengxiong.weixin.redis;

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
public class RedisSingleton extends Jedis {

	private JedisPoolConfig config;
	private JedisPool pool;
	private String ip = "127.0.0.1";
	private int port = 6379;
	private Integer poolMaxTotal = 1024;
	private Integer poolMaxIdle = 1000;
	private Long poolMaxWaitMillis = 200L;
	private Boolean poolTestOnBorrow = true;
	private Boolean poolTestOnReturn = true;
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
		if (config == null) {
			config = new JedisPoolConfig();
			// 最大连接数
			config.setMaxTotal(poolMaxTotal);
			// 最大空闲连接数
			config.setMaxIdle(poolMaxIdle);
			// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
			// 默认-1
			config.setMaxWaitMillis(poolMaxWaitMillis);
			// 在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(poolTestOnBorrow);
			// 在获取返回结果的时候检查有效性, 默认false
			config.setTestOnReturn(poolTestOnReturn);
		}
		if (pool == null) {
			// 初始化连接池
			pool = new JedisPool(config, ip, port);
		}
		return pool;
	}

	public JedisPoolConfig getConfig() {
		return config;
	}

	public void setConfig(JedisPoolConfig config) {
		this.config = config;
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

	public Integer getPoolMaxTotal() {
		return poolMaxTotal;
	}

	public void setPoolMaxTotal(Integer poolMaxTotal) {
		this.poolMaxTotal = poolMaxTotal;
	}

	public Integer getPoolMaxIdle() {
		return poolMaxIdle;
	}

	public void setPoolMaxIdle(Integer poolMaxIdle) {
		this.poolMaxIdle = poolMaxIdle;
	}

	public Long getPoolMaxWaitMillis() {
		return poolMaxWaitMillis;
	}

	public void setPoolMaxWaitMillis(Long poolMaxWaitMillis) {
		this.poolMaxWaitMillis = poolMaxWaitMillis;
	}

	public Boolean getPoolTestOnBorrow() {
		return poolTestOnBorrow;
	}

	public void setPoolTestOnBorrow(Boolean poolTestOnBorrow) {
		this.poolTestOnBorrow = poolTestOnBorrow;
	}

	public Boolean getPoolTestOnReturn() {
		return poolTestOnReturn;
	}

	public void setPoolTestOnReturn(Boolean poolTestOnReturn) {
		this.poolTestOnReturn = poolTestOnReturn;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}
	
	

}
