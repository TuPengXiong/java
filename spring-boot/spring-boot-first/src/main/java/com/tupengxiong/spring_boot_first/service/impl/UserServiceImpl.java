/** 
 * Project Name:spring-boot-first 
 * File Name:UserServiceImpl.java 
 * Package Name:com.tupengxiong.spring_boot_first.service.impl 
 * Date:2017年6月28日下午3:09:43 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.spring_boot_first.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tupengxiong.spring_boot_first.dao.UserDao;
import com.tupengxiong.spring_boot_first.repository.User;
import com.tupengxiong.spring_boot_first.service.UserService;

/**
 * ClassName:UserServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月28日 下午3:09:43 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.8
 * @see
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public User findUserByName(String name) {
		return userDao.findUserByName(name);
	}

	@Override
	public int insert(User user) {
		return userDao.insert(user);
	}

}
