/** 
 * Project Name:redis-session 
 * File Name:LoginController.java 
 * Package Name:com.tupengxiong.redis_session.controller 
 * Date:2017年6月15日下午12:57:54 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.redis_session.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tupengxiong.redis_session.filter.SessionFilter;
import com.tupengxiong.redis_session.redis.RedisSingleton;

/**
 * ClassName:LoginController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月15日 下午12:57:54 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Controller("LoginController")
public class LoginController {

	@RequestMapping("/login.do")
	public void login(String username, String password, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		if (username != null && password != null) {
			RedisSingleton.getJedisInPool().append(username, "exist");
			response.setHeader(SessionFilter.SESSIONANME, username);
			request.getRequestDispatcher("/success.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}

	}
}
