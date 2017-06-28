/** 
 * Project Name:spring-boot-first 
 * File Name:MyWebListener.java 
 * Package Name:com.tupengxiong.spring_boot_first.listener 
 * Date:2017年6月28日上午10:49:06 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.spring_boot_first.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:MyWebListener <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月28日 上午10:49:06 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@WebListener
@Configuration
public class MyWebListener implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(MyWebListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("MyWebListener sce...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("MyWebListener sce...");
	}

}
