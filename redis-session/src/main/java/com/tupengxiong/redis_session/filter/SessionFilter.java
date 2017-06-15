/** 
 * Project Name:redis-session 
 * File Name:SessionFilter.java 
 * Package Name:com.tupengxiong.redis_session.filter 
 * Date:2017年6月15日上午10:32:17 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.redis_session.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tupengxiong.redis_session.redis.RedisSingleton;

/**
 * ClassName:SessionFilter <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月15日 上午10:32:17 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class SessionFilter extends OncePerRequestFilter {

	private static final Logger logger = Logger.getLogger(SessionFilter.class);

	public static final String SESSIONANME = "XSession-Name";

	private String loginUrl = "/index.jsp";
	private String loginAction = "/login.do";
	private Integer expireSecTime = 7200;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String loginPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + loginUrl;
		String loginActionPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + loginAction;
		String currentUrl = request.getRequestURL().toString();
		String sessionValue = request.getHeader(SESSIONANME);
		logger.info(new StringBuilder().append(loginPath));
		logger.info(new StringBuilder().append(loginActionPath));
		logger.info(new StringBuilder().append(currentUrl));
		if (null != sessionValue) {
			// redis 查找是否存在
			if (null == RedisSingleton.getJedisInPool().get(sessionValue)) {
				response.sendRedirect(loginUrl);
			} else {
				RedisSingleton.getJedisInPool().append(sessionValue, "exist");
				RedisSingleton.getJedisInPool().expire(sessionValue, expireSecTime);
				response.setHeader(SESSIONANME, sessionValue);
				filterChain.doFilter(request, response);
			}
		} else if (loginPath.equals(currentUrl) || loginActionPath.equals(currentUrl)) {
			filterChain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + loginUrl);
		}

	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public Integer getExpireSecTime() {
		return expireSecTime;
	}

	public void setExpireSecTime(Integer expireSecTime) {
		this.expireSecTime = expireSecTime;
	}

}
