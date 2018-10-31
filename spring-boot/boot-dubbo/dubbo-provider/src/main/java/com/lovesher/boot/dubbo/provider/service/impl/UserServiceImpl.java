package com.lovesher.boot.dubbo.provider.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.lovesher.boot.dubbo.common.domain.User;
import com.lovesher.boot.dubbo.common.service.UserService;

/**
 * 
 * @ClassName: UserServiceImpl 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tupx 
 * @date 2018年10月29日 上午11:16:45 
 *
 */
@Service(interfaceClass=UserService.class)
@Component
public class UserServiceImpl implements UserService {

    @Override
    public User saveUser(User user) {
        user.setId(1L);
        System.out.println(user.toString());
        return user;
    }
}