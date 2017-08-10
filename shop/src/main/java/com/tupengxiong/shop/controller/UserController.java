package com.tupengxiong.shop.controller;

import com.tupengxiong.shop.beans.User;
import com.tupengxiong.shop.service.impl.DtSpringSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by tpx on 2017/7/10.
 */
@Controller("userController")
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("isAuthenticated()")
    public String info(HttpSession session) {
        User user = dtSpringSecurityService.getUser();
        if (null != user) {
            session.setAttribute("user",user);
        }
        return "index";
    }
}
