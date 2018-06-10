package com.lovesher.entryPoint;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import com.lovesher.filters.login.plugins.LoginFilterPlugin;

@Service("dtLoginUrlAuthenticationEntryPoint")
public class DtLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	@Resource
	protected Map<String, LoginFilterPlugin> loginFilters = new HashMap<String, LoginFilterPlugin>();

	public DtLoginUrlAuthenticationEntryPoint(){
		super(DEFAULT_URL);
	}
	public DtLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	private static final Log LOGGER = LogFactory.getLog(DtLoginUrlAuthenticationEntryPoint.class);

	private static final String DEFAULT_URL = "/login"; 
	/**
	 * 选择登录方式的参数名。
	 */
	public static final String LOGIN_SERVICE = "loginService";

	@Override
	protected String determineUrlToUseForThisRequest(HttpServletRequest request,HttpServletResponse response,AuthenticationException exception) {
	    String loginService = request.getParameter(LOGIN_SERVICE);
	    if (null == loginService) {
	    	loginService = DEFAULT_URL;
	    }else{
	    	LoginFilterPlugin loginFilterPlugin = loginFilters.get(loginService);
	    	if(loginFilterPlugin == null){
	    		LOGGER.error("loginService " + loginService + "can not find ,do by default url:" + DEFAULT_URL);
	    	}
	    	loginService = loginFilterPlugin.getLoginUrl();
	    }
		return loginService;
	}

	public Map<String, LoginFilterPlugin> getLoginFilters() {
		return loginFilters;
	}
}
