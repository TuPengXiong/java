/** 
 * Project Name:spring-boot-first 
 * File Name:UserDaoImpl.java 
 * Package Name:com.tupengxiong.spring_boot_first.dao.impl 
 * Date:2017年6月28日下午3:03:21 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.spring_boot_first.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tupengxiong.spring_boot_first.dao.UserDao;
import com.tupengxiong.spring_boot_first.mapper.UserMapper;
import com.tupengxiong.spring_boot_first.repository.User;

/** 
 * ClassName:UserDaoImpl <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年6月28日 下午3:03:21 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
@Component("userDao")
public class UserDaoImpl implements UserDao{

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findUserByName(String name) {
		return userMapper.findUserByName(name);
	}

	@Override
	public int insert(User user) {
		return userMapper.insert(user);
	}

}
  