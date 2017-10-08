package com.lovesher.tapechat.controller;

import com.lovesher.tapechat.beans.User;
import com.lovesher.tapechat.common.MsgEnum;
import com.lovesher.tapechat.dao.UserDao;
import com.lovesher.tapechat.service.impl.DtSpringSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by tpx on 2017/7/10.
 */
@RestController("userController")
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;
    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("isAuthenticated()")
    public UnionResp<User> info() {
        User user = dtSpringSecurityService.getUser();
        user.setPassword(null);
        return new UnionResp(MsgEnum.SUCCESS.getCode(),MsgEnum.SUCCESS.getMsg(),user);
    }
}
