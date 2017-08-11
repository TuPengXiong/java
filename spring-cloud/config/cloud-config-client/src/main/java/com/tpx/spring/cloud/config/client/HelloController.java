package com.tpx.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {
    @Value("${name}")
    private String name;

    @RequestMapping("/")
    public String from() {
        return "hello ~" + this.name;
    }
}