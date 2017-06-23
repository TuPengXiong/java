/** 
 * Project Name:spring-oauth-resource 
 * File Name:MyResourceServerConfigurer.java 
 * Package Name:org.spring.oauth.resource.config 
 * Date:2017年6月22日下午2:04:49 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package org.spring.oauth.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;

/** 
 * ClassName:MyResourceServerConfigurer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年6月22日 下午2:04:49 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
@EnableResourceServer
@Configuration
public class MyResourceServerConfigurer extends ResourceServerConfigurerAdapter{

    @Autowired
    private RemoteTokenServices remoteTokenServices;
    
    @Bean
    RemoteTokenServices remoteTokenServices(RestTemplate restTemplateBuilder) {
        RemoteTokenServices ts = new RemoteTokenServices();
        // 去检查token的URL
        ts.setCheckTokenEndpointUrl("http://localhost:10010/spring-oauth-server/oauth/check_token");
        // 去检查token时，自己的身份信息
        ts.setClientId("spring-oauth-source-clientId");
        ts.setClientSecret("spring-oauth-source-clientSecret");
        // 内部默认自己新建的，为了方便跟踪调试，统一使用自己配置的。
        ts.setRestTemplate(restTemplateBuilder);
        return ts;
    }
    
    // 先调用 @ ResourceServerConfiguration.configure(HttpSecurity)
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("spring-oauth-resource")
        .tokenServices(remoteTokenServices) //RemoteTokenServices
        .stateless(true);
	}

	// 后调用 @ ResourceServerConfiguration.configure(HttpSecurity)
	@Override
	public void configure(HttpSecurity http) throws Exception {
		 // @formatter:off
        http.requestMatchers()
                .antMatchers("/api/**")

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // 权限的配置建议统一使用 @PreAuthorize 等注解来处理。
            .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();

                //.disable()

        // @formatter:on
	}

	public RemoteTokenServices getRemoteTokenServices() {
		return remoteTokenServices;
	}

	public void setRemoteTokenServices(RemoteTokenServices remoteTokenServices) {
		this.remoteTokenServices = remoteTokenServices;
	}
}
  