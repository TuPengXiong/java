/** 
 * Project Name:spring-boot-first 
 * File Name:MyWebMvcConfigurerAdapter.java 
 * Package Name:com.tupengxiong.spring_boot_first.config 
 * Date:2017年6月28日上午9:44:56 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.spring_boot_first.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/** 
 * ClassName:MyWebMvcConfigurerAdapter <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年6月28日 上午9:44:56 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.8
 * @see       
 */
@Configuration
//@EnableWebMvc 不启用自定义 默认使用spring boot的
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{

	/**
	 * 跨域请求支持
	 * TODO 简单描述该方法的实现功能（可选）. 
	 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry)
	 */
	 @Override
     public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/api/**");
     }
}
  