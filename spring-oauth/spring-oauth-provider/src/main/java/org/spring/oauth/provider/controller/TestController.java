/** 
 * Project Name:spring-oauth-provider 
 * File Name:TestController.java 
 * Package Name:org.spring.oauth.provider.controller 
 * Date:2017年6月22日下午3:08:21 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package org.spring.oauth.provider.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** 
 * ClassName:TestController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年6月22日 下午3:08:21 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
	
	private static final Log logger = LogFactory.getLog(TestController.class);
	@PreAuthorize("permitAll")
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		logger.debug("==============test=================");
		return "index";
	}
}
  