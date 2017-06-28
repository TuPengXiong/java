/** 
 * Project Name:spring-boot-first 
 * File Name:UserDao.java 
 * Package Name:com.tupengxiong.spring_boot_first.dao 
 * Date:2017年6月28日下午3:01:39 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.spring_boot_first.dao;

import com.tupengxiong.spring_boot_first.repository.User;

/**
 * ClassName:UserDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月28日 下午3:01:39 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.8
 * @see
 */
public interface UserDao {

	User findUserByName(String name);

	int insert(User user);
}
