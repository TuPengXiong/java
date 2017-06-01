package com.tupengxiong.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

public class TestRedis {

	public static void main(String[] args) {
		//JedisShardInfo shardInfo = new JedisShardInfo("127.0.0.1", 6379, 5000, 5000, 1);
		//Jedis jedis = RedisSingleton.getJedis(shardInfo);
		Jedis jedis = RedisSingleton.getJedisInPool();
		redisTestForString(jedis);
		// 断开连接
		jedis.disconnect();
	}

	public static void redisTestForString(Jedis jedis) {
		// 值是否设置
		System.out.println(jedis.exists("name"));

		// 设置值
		jedis.set("name", "tupx");
		// 获取值
		System.out.println(jedis.get("name"));

		// 不存在新增 存在叠加
		jedis.append("name", "001");
		// 获取值
		System.out.println(jedis.get("name"));

		// 删除值
		System.out.println(jedis.del("name"));
		// 值是否设置
		System.out.println(jedis.exists("name"));

		// 类型 -- "none",* "string", "list", "set"
		System.out.println(jedis.type("name"));

		// 不存在新增 存在叠加
		jedis.append("name", "001");
		// 获取值
		System.out.println(jedis.get("name"));

		// 10秒后过期
		jedis.expire("name", 10);

	}
}
