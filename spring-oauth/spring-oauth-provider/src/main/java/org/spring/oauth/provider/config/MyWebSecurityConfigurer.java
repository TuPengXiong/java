/** 
 * Project Name:spring-oauth-provider 
 * File Name:MyWebSecurityConfigurer.java 
 * Package Name:org.spring.oauth.provider.config 
 * Date:2017年6月21日下午7:32:32 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.config;

import javax.annotation.Resource;

import org.spring.oauth.provider.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ClassName:MyWebSecurityConfigurer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月21日 下午7:32:32 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Configuration
@EnableWebSecurity
@Order(2)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Resource
	MyUserDetailsService myUserDetailsService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(7);
	}

	@Resource
	PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// 只对以下路径规则应用该安全设置。
		http.requestMatchers()

		.antMatchers("/**"); // 虽然匹配所有路径，但是优先级最低。

		http.formLogin().loginProcessingUrl("/login").loginPage("/login.jsp").defaultSuccessUrl("/login_success.html")
				.failureUrl("/login.jsp?authentication_error=error").permitAll();
				/*
				 * http.addFilterBefore(wxLoginFilter,
				 * UsernamePasswordAuthenticationFilter)
				 * http.addFilterBefore(phoneLoginFilter,
				 * UsernamePasswordAuthenticationFilter)
				 */

		// http.formLogin().loginPage("/phoneLogin").permitAll();

		http.logout().logoutUrl("/logout").logoutSuccessUrl("/login.jsp") // /login?logout
				.permitAll();

		http.authorizeRequests().antMatchers("/")
				.permitAll()/* .anyRequest().authenticated() */;

		http.csrf().disable();

		http.httpBasic();
		/*
		 * http.exceptionHandling() .authenticationEntryPoint(authEntryPoint)
		 */
	}
}
