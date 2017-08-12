package com.tpx.spring.cloud.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tpx on 2017/8/12.
 */
@SpringBootApplication
@EnableEurekaClient //服务注册与发现
@EnableFeignClients //远程服务调用 http
@EnableHystrix  //@EnableCircuitBreaker 等价 熔断 第三方API不可用时fallback
@EnableHystrixDashboard  //熔断监测
//@EnableDiscoveryClient //Spring Cloud选型是中立的因此抽象出该接口， 当服务注册中心选型改变为Eureka，ZK，Consul时，不需要修改原有代码中的注解
public class ClientApplication {
    public static void main(String[] args) {
        //下面两行代码都可以用来启动
        SpringApplication.run(ClientApplication.class, args);
        //new SpringApplicationBuilder(Application.class).web(true).run(args);
    }
}
