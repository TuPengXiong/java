package com.lovesher.tapechat.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tpx on 2017/7/27.
 */
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConf extends BinaryWebSocketHandler implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info(session.getPrincipal().getName() + "connected..");
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.info(session.getPrincipal().getName() + "handleTransportError..");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(session.getPrincipal().getName() + "afterConnectionClosed..");
    }
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.debug(session.getRemoteAddress().getHostName() + session.getRemoteAddress().getPort());
        logger.debug(message.getPayload());
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //消息处理  表示允许连接的域名，withSockJS()方法表示支持以SockJS方式连接服务器
        registry.addHandler(this, "/websocket")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
                //.setAllowedOrigins("*");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //表示允许连接的域名，withSockJS()方法表示支持以SockJS方式连接服务器
        registry.addEndpoint("/webSocket")
                //.setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");//表示客户单向服务器端发送时的主题上面需要加"/app"作为前缀
        config.enableSimpleBroker("/topic", "/user"); //表示在topic和user这两个域上可以向客户端发消息
        config.setUserDestinationPrefix("/user"); //发送一对一的主题前缀是"/user"
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return true;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    }
}
