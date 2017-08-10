package com.tupengxiong.shop.conf;

import com.tupengxiong.shop.security.RestAuthenticationFailureHandler;
import com.tupengxiong.shop.security.RestAuthenticationSuccessHandler;
import com.tupengxiong.shop.service.impl.DtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * Created by tpx on 2017/7/12.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true , prePostEnabled = true)
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

    @Resource
    private DtUserDetailsService dtUserDetailsService;

    @Resource
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Resource
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/**").permitAll() //匿名访问
                    .antMatchers("/api/**").authenticated() //认证访问
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .loginPage("/")//登陆页面
                    .usernameParameter("username")//登陆用户名参数
                    .passwordParameter("password")//登陆密码参数
                //.defaultSuccessUrl("/api/user/name") //登录成功访问
                    .failureHandler(restAuthenticationFailureHandler)
                    .successHandler(restAuthenticationSuccessHandler)
                    .permitAll()   //登陆页匿名访问
                .and()
                    .logout()
                    .permitAll()   //退出登陆允许访问
                .and()
                    .cors()
                .and()
                    .csrf()
                    .disable(); //防注入
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(dtUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
