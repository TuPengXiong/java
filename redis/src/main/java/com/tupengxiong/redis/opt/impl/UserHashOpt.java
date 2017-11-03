/** 
 * Project Name:redis 
 * File Name:TestSet.java 
 * Package Name:com.tupengxiong.redis.set 
 * Date:2017年11月2日上午9:58:01 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.redis.opt.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.tupengxiong.bean.User;
import com.tupengxiong.redis.OptHash;

/**
 * ClassName:UserSetOpt <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月2日 上午9:58:01 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class UserHashOpt extends OptHash<User, Long> {

	private final String KEY = getRedisKey(User.class);
	
	public boolean add(User user) {
		if (null != user && user.getId() != null) {
			// 是否存在
			if (getRedis().hexists(KEY, user.getId().toString())) {
				return false;
			}
			// 添加是否成功
			if (getRedis().hset(KEY, user.getId().toString(), JSON.toJSONString(user)) == 1) {
				return true;
			}
		}
		return false;
	}

	public boolean del(Long userId) {
		if (null != userId) {
			// 是否存在
			if (getRedis().hexists(KEY, userId.toString())) {
				// 删除
				if (getRedis().hdel(KEY, userId.toString()) == 1) {
					return true;
				}
			}
			
		}
		return false;
	}

	public boolean update(User user) {
		if (null != user && user.getId() != null) {
			// 是否存在
			if (getRedis().hexists(KEY, user.getId().toString())) {
				// 更新
				if (getRedis().hset(KEY, user.getId().toString(), JSON.toJSONString(user)) == 0) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<User>();
		// 获取所有成员
		Set<String> set = getRedis().hkeys(KEY);
		for (String key : set) {
			User user = JSON.parseObject(getRedis().hget(KEY, key.toString()), User.class);
			users.add(user);
		}
		return users;
	}

	@Override
	public long getSize() {
		return getRedis().hlen(KEY);
	}

	@Override
	public User get(Long userId) {
		// 是否存在
		if (getRedis().hexists(KEY, userId.toString())) {
			return JSON.parseObject(getRedis().hget(KEY, userId.toString()), User.class);
		}
		return null;
	}
}
