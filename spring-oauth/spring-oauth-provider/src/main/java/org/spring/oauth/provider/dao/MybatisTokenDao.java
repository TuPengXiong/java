package org.spring.oauth.provider.dao;

import org.spring.oauth.provider.entity.MybatisOauth2AccessToken;
import org.spring.oauth.provider.entity.MybatisOauth2RefreshToken;
import org.spring.oauth.provider.service.MyClientDetailService;

public interface MybatisTokenDao {    
    void storeAccessToken(MybatisOauth2AccessToken token);  
      
     MybatisOauth2AccessToken readAccessToken(String tokenValue);  
  
     void removeAccessToken(String tokenValue);  
  
     void storeRefreshToken(MybatisOauth2RefreshToken token);  
  
     MybatisOauth2RefreshToken readRefreshToken(String tokenValue);  
  
     void removeRefreshToken(String tokenValue);  
  
     void removeAccessTokenUsingRefreshToken(String refreshToken);  
  
     MybatisOauth2AccessToken getAccessToken(String authentication);  
     
     MyClientDetailService loadClientByClientId(String clientId);  
}