package com.tpx.spring.cloud.eureka.user.client.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tpx on 2017/8/12.
 */
@RestController
public class UserController {
    @RequestMapping("/user/{id}")
    public String info(@PathVariable("id") Integer id) {
        System.out.println("==========${id}===============:" + id);
        return "eureka-client-user：" + id;
    }
}
