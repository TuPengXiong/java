package com.tupengxiong.logistics.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60*3)
public class SessionConf {

    private static final Logger log = LoggerFactory.getLogger(SessionConf.class);

}