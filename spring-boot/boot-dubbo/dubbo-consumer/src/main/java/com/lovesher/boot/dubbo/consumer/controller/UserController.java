package com.lovesher.boot.dubbo.consumer.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lovesher.boot.dubbo.consumer.service.UserConsumerService;

/**
 * 
 * @ClassName: UserController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tupx 
 * @date 2018年10月29日 下午2:54:52 
 *
 */
@RestController
public class UserController {

    @Autowired
    private UserConsumerService service;


    @RequestMapping("/save")
    public Object saveUser() {

        return service.saveUser();
    }
}