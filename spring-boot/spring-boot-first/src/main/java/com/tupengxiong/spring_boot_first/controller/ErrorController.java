/** 
 * Project Name:spring-boot-first 
 * File Name:ErrorController.java 
 * Package Name:com.tupengxiong.spring_boot_first.controller 
 * Date:2017年6月28日上午9:51:17 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.spring_boot_first.controller;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;

/**
 * ClassName:ErrorController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月28日 上午9:51:17 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.8
 * @see
 */
public class ErrorController extends BasicErrorController {

	public ErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
		super(errorAttributes, errorProperties);
	}

}
