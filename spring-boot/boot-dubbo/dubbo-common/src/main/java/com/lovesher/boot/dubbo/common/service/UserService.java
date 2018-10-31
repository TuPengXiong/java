package com.lovesher.boot.dubbo.common.service;

import com.lovesher.boot.dubbo.common.domain.User;

/**
 * 
 * @ClassName: UserService 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tupx 
 * @date 2018年10月29日 上午11:16:40 
 *
 */
public interface UserService {

    User saveUser(User user);
}