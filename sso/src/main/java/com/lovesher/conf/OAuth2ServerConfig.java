package com.lovesher.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @see org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint 
 * 	#/oauth/authorize TokenEndpoint /oauth/token
 * @see org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint 
 * 	/oauth/check_token 
 * @see org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint 
 * /oauth/confirm_access /oauth/error
 * 
 * @ClassName: OAuth2ServerConfig
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: tupengxiong tupengxiong@qq.com
 * @date: 2018年6月9日 下午10:05:57
 * @version V1.0
 */
@Configuration
public class OAuth2ServerConfig {
	private static final String RESOURCE_ID = "sso-sever";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.resourceId(RESOURCE_ID).stateless(true);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
					// Since we want the protected resources to be accessible in
					// the UI as well we need
					// session creation to be allowed (it's disabled by default
					// in 2.0.6)
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
					.requestMatchers().anyRequest().and().anonymous().and().authorizeRequests()
					// .antMatchers("/product/**").access("#oauth2.hasScope('select')
					// and hasRole('ROLE_USER')")
					.antMatchers("/order/**").authenticated();// 配置order访问控制，必须认证过后才可以访问
			// @formatter:on
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

		@Autowired
		ClientDetailsService dtClientDetailsService;
		@Autowired
		RedisConnectionFactory redisConnectionFactory;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

			clients.withClientDetails(dtClientDetailsService);
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.prefix(RESOURCE_ID)
					.tokenStore(new RedisTokenStore(redisConnectionFactory))
					.allowedTokenEndpointRequestMethods(HttpMethod.values());
			endpoints.setClientDetailsService(dtClientDetailsService);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			// 允许表单认证
			oauthServer.allowFormAuthenticationForClients();
		}
	}
}