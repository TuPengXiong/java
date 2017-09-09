package com.tupengxiong.common.service.impl;

import com.tupengxiong.common.beans.User;
import com.tupengxiong.common.dao.UserDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tpx on 2017/7/22.
 */
@Service("dtSpringSecurityService")
public class DtSpringSecurityService {

    @Resource
    private UserDao userDao;

    public User getUser() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            return userDao.findByUsername(username);
        }catch (Exception ex){

        }
        return null;
    }
}
