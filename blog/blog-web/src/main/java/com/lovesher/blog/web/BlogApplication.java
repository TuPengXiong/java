package com.lovesher.blog.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;


/**
 * 
 * ClassName: BlogApplication <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2018年3月1日 下午11:52:46 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
@SpringBootApplication
@PropertySource(value={"classpath:blog.properties"})
@ImportResource(locations={"classpath:applicationContext.xml"})
public class BlogApplication 
{
	public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
