/** 
 * Project Name:spring-oauth-provider 
 * File Name:MybatisOauth2AccessToken.java 
 * Package Name:org.spring.oauth.provider.entity 
 * Date:2017年6月22日上午11:21:14 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.entity;

import java.io.Serializable;

/**
 * ClassName:MybatisOauth2AccessToken <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月22日 上午11:21:14 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class MybatisOauth2AccessToken implements Serializable {

	private static final long serialVersionUID = -4232065232755289541L;

	private String tokenId;
	private byte[] token;
	private String authenticationId;
	private byte[] authentication;
	private String refreshToken;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public byte[] getToken() {
		return token;
	}

	public void setToken(byte[] token) {
		this.token = token;
	}

	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
