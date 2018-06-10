package com.lovesher.conf;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lovesher.entryPoint.DtLoginUrlAuthenticationEntryPoint;
import com.lovesher.filters.login.plugins.LoginFilterPlugin;
import com.lovesher.service.DtUserDetailsService;

/**
 * Created by tpx on 2017/7/12.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true , prePostEnabled = true)
public class WebSecurityConf extends WebSecurityConfigurerAdapter {

	@Autowired
    private DtUserDetailsService dtUserDetailsService;
    @Autowired
    private DtLoginUrlAuthenticationEntryPoint dtLoginUrlAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/oauth/*").permitAll() //匿名访问
                    .antMatchers("/api/**").authenticated() //认证访问
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()   //登陆页匿名访问
                .and()
                    .logout()
                    .logoutSuccessUrl("/logoutSuccess")
                    .permitAll()   //退出登陆允许访问
                .and()
                	.exceptionHandling().authenticationEntryPoint(dtLoginUrlAuthenticationEntryPoint)
                .and()
                    .cors()
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .csrf()
                    .disable(); //防注入
        
			    //@see org.springframework.security.config.annotation.web.builders.FilterComparator
			    //LoginUrlAuthenticationEntryPoint
			    //	.addFilterBefore(new Phonefilter(), UsernamePasswordAuthenticationFilter.class)
                for(LoginFilterPlugin loginFilterPlugin:dtLoginUrlAuthenticationEntryPoint.getLoginFilters().values()){
                	http
		                .formLogin()
		                .loginPage(loginFilterPlugin.getLoginUrl())
		                .permitAll();   //登陆页匿名访问
                	http.addFilterBefore(loginFilterPlugin, UsernamePasswordAuthenticationFilter.class);
                }
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
