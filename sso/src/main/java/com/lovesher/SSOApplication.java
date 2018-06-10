package com.lovesher;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lovesher.beans.User;
import com.lovesher.dao.UserDao;


/**
 * Created by tpx on 2017/7/10.
 */
@SpringBootApplication(scanBasePackages = "com.lovesher")
@EnableJpaRepositories(basePackageClasses = UserDao.class)
@EntityScan(basePackageClasses = User.class)
@ControllerAdvice
@RestController
public class SSOApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SSOApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SSOApplication.class, args);
    }
    
    /**
     * 
     * 错误处理器
     *
     * @param e
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler
    @ResponseBody
    public Map handleIOException(HttpServletRequest request,HttpServletResponse response,Model model,Exception e) {
        logger.error("请求发生错误", e);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", -1);
        map.put("message", e.getMessage());
        map.put("cause", e.getCause());
        return map;
    }
}
