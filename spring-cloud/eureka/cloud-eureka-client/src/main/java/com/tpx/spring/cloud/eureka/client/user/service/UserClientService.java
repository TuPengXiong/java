package com.tpx.spring.cloud.eureka.client.user.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by tpx on 2017/8/12.
 */
@Service("userClientService")
@FeignClient(name = "eureka-client-user")
public interface UserClientService {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    String info(@PathVariable("id") Integer id);
}
