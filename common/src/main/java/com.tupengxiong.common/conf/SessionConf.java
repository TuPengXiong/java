package com.tupengxiong.common.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.concurrent.CountDownLatch;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60*60*3)
public class SessionConf {

    private static final Logger log = LoggerFactory.getLogger(SessionConf.class);

}