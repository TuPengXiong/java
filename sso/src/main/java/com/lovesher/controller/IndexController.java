package com.lovesher.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lovesher.beans.User;
import com.lovesher.dao.UserDao;

/**
 * Created by tpx on 2017/10/27.
 */
@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserDao userDao;
    @PostMapping("/index")
    User index(@RequestParam("id") String id){
        logger.info("id==============>"+id);
        return userDao.findByUsername(id);
    }
}
