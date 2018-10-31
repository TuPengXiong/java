package com.lovesher.boot.dubbo.consumer.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lovesher.boot.dubbo.common.domain.User;
import com.lovesher.boot.dubbo.common.service.UserService;

/**
 * 
 * @ClassName: UserConsumerService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tupx 
 * @date 2018年10月29日 上午11:27:42 
 *
 */
@Component("userConsumerService")
public class UserConsumerService {

	@Reference
	private UserService userService;

	public User saveUser() {
		System.out.println("UserConsumerService");
        User user = new User();
        user.setUsername("jaycekon");
        user.setPassword("jaycekong824");
        return userService.saveUser(user);
    }
}