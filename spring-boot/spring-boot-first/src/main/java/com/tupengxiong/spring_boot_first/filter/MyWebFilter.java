/** 
 * Project Name:spring-boot-first 
 * File Name:MyWebFilter.java 
 * Package Name:com.tupengxiong.spring_boot_first.filter 
 * Date:2017年6月28日上午10:43:13 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.spring_boot_first.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

/** 
 * ClassName:MyWebFilter <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年6月28日 上午10:43:13 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.8
 * @see       
 */
@WebFilter
@Configuration
public class MyWebFilter implements Filter{

	private static final Log logger = LogFactory.getLog(MyWebFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("MyWebFilter start...");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("MyWebFilter doFilter...");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("MyWebFilter destroy...");
	}

}
  