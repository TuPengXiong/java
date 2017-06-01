package com.tupengxiong.redis;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

/**
 * @author tupengxiong RedisSingleton redis操作 单例模式实现
 */
public class RedisSingleton extends Jedis {

	private static Jedis jedis;
	private static JedisPoolConfig config;
	private static JedisPool pool;
	private static Properties pro = new Properties();

	/**
	 * 构造方法私有化
	 */
	private RedisSingleton() {

	}

	/**
	 * 初始化JedisPool
	 */
	private static void initJedisPool() {
		if (pool == null) {
			config = getPoolConfig();
			// 获取服务器IP地址
			String ipStr = pro.getProperty("redis.ip");
			// 获取服务器端口
			int portStr = Integer.valueOf(pro.getProperty("redis.port"));
			// 初始化连接池
			pool = new JedisPool(config, ipStr, portStr);
		}
	}

	public static synchronized Jedis getJedis(JedisShardInfo shardInfo) {
		if (null == jedis) {
			jedis = new Jedis(shardInfo);
		}
		return jedis;
	}

	public static synchronized Jedis getJedisInPool() {
		if (null == jedis) {
			initJedisPool();
			jedis = pool.getResource();
		}
		return jedis;
	}

	/**
	 * 获取化连接池配置
	 * 
	 * @return JedisPoolConfig
	 */
	private static JedisPoolConfig getPoolConfig() {
		if (config == null) {
			loadOnProp(RedisSingleton.class, "redis.properties");
			config = new JedisPoolConfig();
			// 最大连接数
			config.setMaxTotal(Integer.valueOf(pro.getProperty("jedis.pool.maxTotal")));
			// 最大空闲连接数
			config.setMaxIdle(Integer.valueOf(pro.getProperty("jedis.pool.maxIdle")));
			// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
			// 默认-1
			config.setMaxWaitMillis(Long.valueOf(pro.getProperty(("jedis.pool.maxWaitMillis"))));
			// 在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(Boolean.valueOf(pro.getProperty("jedis.pool.testOnBorrow")));
			// 在获取返回结果的时候检查有效性, 默认false
			config.setTestOnReturn(Boolean.valueOf(pro.getProperty("jedis.pool.testOnReturn")));
		}
		return config;
	}

	/**
	 * 加载配置文件
	 * 
	 * @param className
	 * @param fileName
	 */
	private static void loadOnProp(Class<?> className, String fileName) {
		InputStream in =  className.getResourceAsStream(fileName);
		System.out.println(ClassLoader.getSystemResourceAsStream(fileName));
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
