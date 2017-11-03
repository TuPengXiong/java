
package com.tupengxiong.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * ClassName:OptSet <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月2日 上午9:58:29 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 * 
 * Redis 中每个 hash 可以存储 232 - 1 键值对（40多亿）。
 */
public abstract class OptHash<V,T> {

	public  String getRedisKey(Class<V> claz) {
		return claz.getName().replace(".", ":")+":hash:";
	}
	public abstract long getSize();
	
	public abstract boolean add(V v);
	
	public abstract List<V> getAll();
	
	public abstract V get(T t);

	public abstract boolean del(T t);

	public abstract boolean update(V v);

	public Jedis getRedis() {
		return RedisSingleton.getJedisInPool();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
