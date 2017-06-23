package org.spring.oauth.resource.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 对 Spring Security 进行配置
 */
@Configuration
@Order(2)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
class MyWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    void configAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("a_admin")
                .password("a_admin")
                .authorities("AAA")
                .roles("ADMIN", "USER")

            .and()
                .withUser("a_user")
                .password("a_user")
                .authorities("UUU")
                .roles("USER");
        // @formatter:on
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {



        // @formatter:off
        // 只对以下路径规则应用该安全设置。
        http.requestMatchers()
                .antMatchers("/")
                .antMatchers("/login")
                .antMatchers("/sec")
                .antMatchers("/oauth/authorize")
                .antMatchers("/oauth/confirm_access")

            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()

            .and()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()

                .anyRequest()
                .authenticated()

            .and()
                .httpBasic()
                .realmName("My OAuth2 Auth Server")

            .and()
                .csrf(); // 仅仅测试用
               /* .ignoringAntMatchers("/login")
                .ignoringAntMatchers("/oauth/confirm_access")
                .ignoringAntMatchers("/oauth/authorize");*/
        // @formatter:on
    }

}