/** 
 * Project Name:spring-oauth-provider 
 * File Name:MyUserDetailsService.java 
 * Package Name:org.spring.oauth.provider.service 
 * Date:2017年6月23日上午9:38:37 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * ClassName:MyUserDetailsService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月23日 上午9:38:37 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	private static final Log logger = LogFactory.getLog("MyUserDetailsService");
	@Resource
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		GrantedAuthority authority = new SimpleGrantedAuthority("LOGIN");
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(authority);
		String password = passwordEncoder.encode(username);
		User user = new User(username, password, authorities);
		logger.debug("===============================");
		logger.debug(user);
		logger.debug("===============================");
		return user;
	}

}
