/** 
 * Project Name:spring-boot-first 
 * File Name:UserService.java 
 * Package Name:com.tupengxiong.spring_boot_first.service 
 * Date:2017年6月28日下午3:08:58 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.spring_boot_first.service;

import com.tupengxiong.spring_boot_first.repository.User;

/** 
 * ClassName:UserService <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年6月28日 下午3:08:58 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.8
 * @see       
 */
public interface UserService {

	User findUserByName(String name);

	int insert(User user);
}
  