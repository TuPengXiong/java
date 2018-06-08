package com.lovesher.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lovesher.dao.UserDao;

/**
 * Created by tpx on 2017/7/12.
 */
@Service("dtUserDetailsService")
public class DtUserDetailsService implements UserDetailsService {


    private  final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private UserDao userDao;
    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        logger.info("username=========:"+username);
        logger.info(passwordEncoder.encode(username));
        com.lovesher.beans.User u = userDao.findByUsername(username);
        if(u == null){
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("LOGIN"));
        User user = new User(u.getUsername(),u.getPassword(),authorities);
        return  user;
    }
}
