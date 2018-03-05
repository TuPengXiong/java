/** 
 * Project Name:blog-web 
 * File Name:TestController.java 
 * Package Name:com.lovesher.blog.web.controller 
 * Date:2018年3月5日下午6:12:34 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.blog.web.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovesher.blog.service.UserService;

/** 
 * ClassName:TestController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月5日 下午6:12:34 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
@RestController
public class TestController {

	@Autowired
	private UserService userService;
	@RequestMapping("/")
	String index(){
		
		userService.selectById(1L);
		return "blog" + System.currentTimeMillis();
	}
}
  