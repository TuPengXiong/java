package com.tpx.spring.cloud.eureka.client.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tpx.spring.cloud.eureka.client.VerifyCodeUtils;
import com.tpx.spring.cloud.eureka.client.user.service.UserClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by tpx on 2017/8/12.
 */
@RestController
public class UserController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private UserClientService userClientService;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/user/{id}")
    public String info(@PathVariable("id") Integer id) {
        return userClientService.info(id);
    }

    public String fallback(Integer id) {
        return "some exception occur call fallback method.";
    }

    @RequestMapping("/registered")
    public List<String> getRegistered() {
        List<ServiceInstance> list = discoveryClient.getInstances("STORES");
        System.out.println(discoveryClient.getLocalServiceInstance());
        System.out.println("discoveryClient.getServices().size() = " + discoveryClient.getServices().size());
        for (String s : discoveryClient.getServices()) {
            System.out.println("services " + s);
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(s);
            for (ServiceInstance si : serviceInstances) {
                System.out.println("    services:" + s + ":getHost()=" + si.getHost());
                System.out.println("    services:" + s + ":getPort()=" + si.getPort());
                System.out.println("    services:" + s + ":getServiceId()=" + si.getServiceId());
                System.out.println("    services:" + s + ":getUri()=" + si.getUri());
                System.out.println("    services:" + s + ":getMetadata()=" + si.getMetadata());
            }
        }
        System.out.println(list.size());
        if (list != null && list.size() > 0) {
            System.out.println(list.get(0).getUri());
        }
        return discoveryClient.getServices();
    }

    @RequestMapping("/code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

    }
}
