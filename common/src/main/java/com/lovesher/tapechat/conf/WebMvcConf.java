package com.lovesher.tapechat.conf;

import com.lovesher.tapechat.interceptors.SettingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

/**
 * Created by tpx on 2017/7/12.
 */
@Configuration
//@EnableWebMvc //无需使用该注解，否则会覆盖掉SpringBoot的默认配置值
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Resource
    private SettingInterceptor interceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/talk").setViewName("talk");
        registry.addViewController("/excel").setViewName("excel");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
        registry.addMapping("/login");
        registry.addMapping("/talk");
        registry.addMapping("/reg");
        registry.addMapping("/excel");
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
