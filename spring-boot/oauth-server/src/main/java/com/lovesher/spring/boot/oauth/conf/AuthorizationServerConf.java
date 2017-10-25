package com.lovesher.spring.boot.oauth.conf;

import com.lovesher.spring.boot.oauth.service.DtClientDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

/**
 * Created by tpx on 2017/10/24.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConf implements AuthorizationServerConfigurer {

    @Resource
    private DtClientDetailsService dtClientDetailsService;
    @Resource
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.realm("oauth-server");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(dtClientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.clientDetailsService(dtClientDetailsService);
        endpoints.accessTokenConverter(new DefaultAccessTokenConverter());
    }
}
