package com.lovesher.conf;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import com.lovesher.service.DtClientDetailsService;

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
        security.realm("sso-server");
        security.allowFormAuthenticationForClients();
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
