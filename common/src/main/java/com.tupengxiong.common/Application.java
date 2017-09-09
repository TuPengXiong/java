package com.tupengxiong.common;

import com.tupengxiong.common.beans.User;
import com.tupengxiong.common.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * Created by tpx on 2017/7/10.
 */
@SpringBootApplication(scanBasePackages = "com.tupengxiong.common")
@EnableJpaRepositories(basePackageClasses = UserDao.class)
@EntityScan(basePackageClasses = User.class)
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
