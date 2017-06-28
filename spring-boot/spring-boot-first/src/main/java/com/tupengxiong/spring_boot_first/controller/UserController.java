/** 
 * Project Name:spring-boot-first 
 * File Name:UserController.java 
 * Package Name:com.tupengxiong.spring_boot_first.controller 
 * Date:2017年6月28日下午3:12:34 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.spring_boot_first.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tupengxiong.spring_boot_first.repository.User;
import com.tupengxiong.spring_boot_first.service.UserService;

/**
 * ClassName:UserController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月28日 下午3:12:34 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.8
 * @see
 */
@RestController
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping(path = "/user/add", method = RequestMethod.GET)
	String add(User user) {
		int index = userService.insert(user);
		return "success" + index;
	}

	@RequestMapping("/user/find")
	String find(String name) {
		User user = userService.findUserByName(name);
		return user == null ? "not found" : user.toString();
	}

}
