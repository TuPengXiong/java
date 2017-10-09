package com.lovesher.tapechat.service.impl;

import com.lovesher.tapechat.beans.User;
import com.lovesher.tapechat.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tpx on 2017/7/22.
 */
@Service("dtSpringSecurityService")
public class DtSpringSecurityService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserDao userDao;

    public User getUser() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return userDao.findByUsername(username);
        } catch (Exception ex) {
            logger.error("用户未登录!!!");
        }
        return null;
    }
}
