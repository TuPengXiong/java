/** 
 * Project Name:spring-oauth-provider 
 * File Name:MyTokenStore.java 
 * Package Name:org.spring.oauth.provider.store 
 * Date:2017年6月22日上午11:25:46 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.store;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.spring.oauth.provider.dao.MybatisTokenDao;
import org.spring.oauth.provider.entity.MybatisOauth2AccessToken;
import org.spring.oauth.provider.entity.MybatisOauth2RefreshToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

/**
 * ClassName:MyTokenStore <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月22日 上午11:25:46 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
//@Component
public class MyTokenStore implements TokenStore {

	private static final Log LOG = LogFactory.getLog(MyTokenStore.class);

	@Resource
	private MybatisTokenDao mybatisTokenDao;

	private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {

		OAuth2Authentication authentication = null;
		try {
			MybatisOauth2AccessToken at = mybatisTokenDao.readAccessToken(token.getValue());
			authentication = SerializationUtils.deserialize(at.getAuthentication());

		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + token);
			}
		}
		return authentication;
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		OAuth2Authentication authentication = null;
		try {
			MybatisOauth2AccessToken at = mybatisTokenDao.readAccessToken(token);
			authentication = SerializationUtils.deserialize(at.getAuthentication());
		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + token);
			}
		}

		return authentication;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}
		MybatisOauth2AccessToken at = new MybatisOauth2AccessToken();
		at.setTokenId(token.getValue());
		at.setToken(SerializationUtils.serialize(token));
		at.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
		at.setAuthentication(SerializationUtils.serialize(authentication));
		at.setRefreshToken(refreshToken);
		mybatisTokenDao.storeAccessToken(at);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = SerializationUtils.deserialize(mybatisTokenDao.readAccessToken(tokenValue).getToken());
		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + tokenValue);
			}
		}
		return accessToken;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		mybatisTokenDao.removeAccessToken(token.getValue());
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		MybatisOauth2RefreshToken rt = new MybatisOauth2RefreshToken();
		rt.setTokenId(refreshToken.getValue());
		rt.setToken(SerializationUtils.serialize(refreshToken));
		rt.setAuthentication(SerializationUtils.serialize(authentication));
		mybatisTokenDao.storeRefreshToken(rt);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		ExpiringOAuth2RefreshToken refreshToken = null;
		try {
			refreshToken = SerializationUtils.deserialize(mybatisTokenDao.readRefreshToken(tokenValue).getToken());
		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find refresh token for token " + tokenValue);
			}
		}
		return refreshToken;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {

		OAuth2Authentication authentication = null;
		try {
			MybatisOauth2RefreshToken at = mybatisTokenDao.readRefreshToken(token.getValue());
			authentication = SerializationUtils.deserialize(at.getAuthentication());
		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Failed to find access token for token " + token);
			}
		}

		return authentication;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		mybatisTokenDao.removeRefreshToken(token.getValue());
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		mybatisTokenDao.removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {

		OAuth2AccessToken accessToken = null;

		try {
			String auth = authenticationKeyGenerator.extractKey(authentication);
			MybatisOauth2AccessToken at = mybatisTokenDao.getAccessToken(auth);
			if (null == at) {
				return null;
			} else {
				accessToken = SerializationUtils.deserialize(at.getToken());
			}

		} catch (EmptyResultDataAccessException e) {
			if (LOG.isInfoEnabled()) {
				LOG.debug("Failed to find access token for authentication " + authentication);
			}
		}

		return accessToken;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {

		return null;
	}

}
