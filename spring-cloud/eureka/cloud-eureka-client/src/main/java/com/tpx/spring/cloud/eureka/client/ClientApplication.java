package com.tpx.spring.cloud.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by tpx on 2017/8/12.
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableDiscoveryClient //Spring Cloud选型是中立的因此抽象出该接口， 当服务注册中心选型改变为Eureka，ZK，Consul时，不需要修改原有代码中的注解
public class ClientApplication {
    public static void main(String[] args) {
        //下面两行代码都可以用来启动
        SpringApplication.run(ClientApplication.class, args);
        //new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
