package com.lovesher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.lovesher.beans.User;
import com.lovesher.dao.UserDao;


/**
 * Created by tpx on 2017/7/10.
 */
@SpringBootApplication(scanBasePackages = "com.lovesher")
@EnableJpaRepositories(basePackageClasses = UserDao.class)
@EntityScan(basePackageClasses = User.class)
public class SSOApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SSOApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SSOApplication.class, args);
    }
}
