/** 
 * Project Name:spring-oauth-provider 
 * File Name:MyGlobalAuthenticationConfigurer.java 
 * Package Name:org.spring.oauth.provider.config 
 * Date:2017年6月23日上午9:57:13 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.config;

import javax.annotation.Resource;

import org.spring.oauth.provider.service.MyUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyGlobalAuthenticationConfigurer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月23日 上午9:57:13 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
//@Configuration
public class MyGlobalAuthenticationConfigurer extends GlobalAuthenticationConfigurerAdapter {

	//@Resource
	private MyUserDetailsService userDetailsService;
	
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	public void configure(AuthenticationManagerBuilder auth) throws Exception {

	}
}
