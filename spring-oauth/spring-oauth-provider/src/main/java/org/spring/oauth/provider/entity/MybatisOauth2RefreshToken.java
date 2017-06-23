package org.spring.oauth.provider.entity;

import java.io.Serializable;
/**
 * 
 * ClassName: MybatisOauth2RefreshToken <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年6月22日 上午11:24:39 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class MybatisOauth2RefreshToken implements Serializable {
	private static final long serialVersionUID = 238497479380096784L;

	private String tokenId;
	private byte[] token;
	private byte[] authentication;

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

	public byte[] getAuthentication() {
		return authentication;
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication;
	}

}
