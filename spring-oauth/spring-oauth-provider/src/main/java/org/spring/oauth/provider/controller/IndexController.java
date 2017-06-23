/** 
 * Project Name:spring-oauth-provider 
 * File Name:IndexController.java 
 * Package Name:org.spring.oauth.provider.controller 
 * Date:2017年6月23日下午3:31:47 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:IndexController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月23日 下午3:31:47 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller
public class IndexController {

	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("login");
	}
}
