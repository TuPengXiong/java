package com.tupengxiong.shop.controller;

import com.tupengxiong.shop.beans.User;
import com.tupengxiong.shop.common.MsgEnum;
import com.tupengxiong.shop.service.impl.DtSpringSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tpx on 2017/7/10.
 */
@RestController("userController")
@RequestMapping("/api/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;

    @RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PreAuthorize("isAuthenticated()")
    public Map<String, Object> info() {
        Map map = new HashMap<String, Object>();
        User user = dtSpringSecurityService.getUser();
        if (null != user) {
            map.put("id", user.getId());
            map.put("username", user.getUsername());
            map.put("createTime", user.getCreateTime());
            map.put("code", MsgEnum.SUCCESS.getCode());
            map.put("msg", MsgEnum.SUCCESS.getMsg());
        }
        return map;
    }
}
