/** 
 * Project Name:spring-oauth-provider 
 * File Name:MyClientDetailService.java 
 * Package Name:org.spring.oauth.provider.service 
 * Date:2017年6月22日上午10:58:24 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * ClassName:MyClientDetailService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月22日 上午10:58:24 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
@Lazy(false)
public class MyClientDetailService implements ClientDetailsService, ClientDetails {

	private static final long serialVersionUID = 127195537767881725L;
	private static final Log logger = LogFactory.getLog(MyClientDetailService.class);

	/*
	 * @Resource private MybatisTokenDao mybatisTokenDao;
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		logger.debug("==============================");
		logger.debug(clientId);
		logger.debug("===============================");
		return this;
	}

	@Override
	public boolean isSecretRequired() {

		return false;
	}

	@Override
	public boolean isScoped() {

		return true;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		if (scope.contains("LOGIN")) {
			return true;
		}
		return false;
	}

	@Override
	public Set<String> getScope() {
		// "LOGIN", "read", "write"
		Set<String> set = new HashSet<String>();
		set.add("LOGIN");
		set.add("read");
		set.add("write");
		return set;
	}

	@Override
	public Set<String> getResourceIds() {
		Set<String> set = new HashSet<String>();
		set.add("spring-ouath-resource");
		return set;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		Set<String> set = new HashSet<String>();
		set.add("http://baidu.com");
		return set;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {

		return 3600;
	}

	@Override
	public String getClientSecret() {

		return "ClientSecret";
	}

	@Override
	public String getClientId() {

		return "ClientId";
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		Set<String> set = new HashSet<String>();
		set.add("authorization_code");
		set.add("authorization_code");
		set.add("implicit");
		set.add("client_credentials");
		set.add("password");
		set.add("refresh_token");
		return set;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
		set.add(new SimpleGrantedAuthority("ROLE_CLIENT"));
		return set;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {

		return null;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {

		return 3600;
	}
}
