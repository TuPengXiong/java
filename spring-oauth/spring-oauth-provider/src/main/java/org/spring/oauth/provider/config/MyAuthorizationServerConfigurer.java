/** 
 * Project Name:spring-oauth-provider 
 * File Name:MyAuthorizationServerConfigurer.java 
 * Package Name:org.spring.oauth.provider.config 
 * Date:2017年6月21日下午5:38:20 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package org.spring.oauth.provider.config;


import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.annotations.Many;
import org.spring.oauth.provider.service.MyClientDetailService;
import org.spring.oauth.provider.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 *
 * 通过 AuthorizationServerSecurityConfiguration 仅仅过滤以下 URL:
 *      /oauth/token
 *      /oauth/token_key
 *      /oauth/check_token
 *
 * 注意 :  虽然 OAuth2AuthorizationServerConfiguration 可提供默认的配置，但是有以下缺陷：
 *      1. 默认只能从 application.yml 中获取一个 client 身份信息，就意味着只能有一个第三方应用。
 * 故不要使用，自己明确提供 AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {


    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    DataSource dataSource;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Resource
    MyClientDetailService myClientDetailService;
    
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        // 如果集群模式下部署，则这里必须明确指明签名和验证签名的key。
        return new JwtAccessTokenConverter();
    }

    @Bean
    public TokenStore tokenStore(DataSource dataSource) {
//        TokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter())
//        return tokenStore

        // 先创建所需的表结构
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(resourceLoader.getResource("classpath:schema.sql"));
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        TokenStore tokenStore = new JdbcTokenStore(dataSource);
        return tokenStore;
    }

    @Bean
    public ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	clients.withClientDetails(myClientDetailService);

    }

    // AuthorizationServerEndpointsConfiguration#authorizationEndpoint() 已经配置好了
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(tokenStore)
        //.userApprovalHandler(userApprovalHandler)
                .authenticationManager(authenticationManager);  // 启用 ResourceOwnerPasswordTokenGranter
        //.pathMapping("/oauth/confirm_access","/your/controller") // 可以修改映射路径。
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.realm("tupengxiong/oauth2")
                .tokenKeyAccess("isFullyAuthenticated()")
                .checkTokenAccess("isFullyAuthenticated()");
    }

}