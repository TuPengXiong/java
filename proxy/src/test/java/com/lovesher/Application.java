package com.lovesher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.lovesher.interfaces.HelloService;

/*@Configuration
@EnableAutoConfiguration
@ComponentScan*/
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class Application {

    public static void main(String[] args) {
    	ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    	HelloService helloService = applicationContext.getBean(HelloService.class);
        helloService.sayHello("111");
    }

}