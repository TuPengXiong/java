/** 
 * Project Name:redis 
 * File Name:Test.java 
 * Package Name:com.tupengxiong.redis.set 
 * Date:2017年11月2日下午5:57:41 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.redis.opt.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.tupengxiong.bean.User;

/** 
 * ClassName:Test <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年11月2日 下午5:57:41 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class Test {

	public static void main(String[] args) {
		UserHashOpt userHashOpt = new UserHashOpt();
		System.out.println("============================ADD==============================");
		for(Long i=0L;i<100L;i++){
			User user = new User();
			user.setId(i);
			user.setUsername("tupx--"+i);
			user.setPassword("password--"+i);
			user.setUpdateTime(new Date());
			user.setCreateTime(new Date());
			System.out.println("add="+i+"==>"+userHashOpt.add(user));
		}
		System.out.println("============================GETALL==============================");
		List<User> users = userHashOpt.getAll();
		for(int i=0;i<users.size();i++){
			System.out.println("get="+i+"==>"+JSON.toJSONString(users.get(i)));
		}
		
		System.out.println("============================GETONE==============================");
		System.out.println(JSON.toJSONString(userHashOpt.get(1L)));
		
		
		System.out.println("============================UPDATE==============================");
		User user = userHashOpt.get(1L);
		user.setUsername("tupx--001");
		user.setUpdateTime(new Date());
		user.setCreateTime(new Date());
		System.out.println("update"+1+"==>"+userHashOpt.update(user));
		
		System.out.println("============================GETSIZE==============================");
		System.out.println("size="+userHashOpt.getSize());
		
		System.out.println("============================GETSIZE==============================");
		for(Long i=2L;i<100L;i++){
			System.out.println("del="+i+"==>"+userHashOpt.del(i));
		}
	}
}
  