package com.lovesher.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by tpx on 2017/7/12.
 */
@Configuration
//@EnableWebMvc //无需使用该注解，否则会覆盖掉SpringBoot的默认配置值
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }
}
