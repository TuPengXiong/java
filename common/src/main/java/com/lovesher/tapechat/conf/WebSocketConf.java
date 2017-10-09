package com.lovesher.tapechat.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
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
public class WebSocketConf extends AbstractSecurityWebSocketMessageBrokerConfigurer implements WebSocketConfigurer, WebSocketHandler {

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

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //消息处理  表示允许连接的域名，withSockJS()方法表示支持以SockJS方式连接服务器
        registry.addHandler(this, "/webSocket")
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
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            handleTextMessage(session, (TextMessage) message);
        } else if (message instanceof BinaryMessage) {
            handleBinaryMessage(session, (BinaryMessage) message);
        } else if (message instanceof PongMessage) {
            handlePongMessage(session, (PongMessage) message);
        } else {
            throw new IllegalStateException("Unexpected WebSocket message type: " + message);
        }
    }

    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info(session.getRemoteAddress().getHostName() + session.getRemoteAddress().getPort());
        logger.info(message.getPayload());
    }

    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        logger.info(session.getRemoteAddress().getHostName() + session.getRemoteAddress().getPort());
        logger.info(new String(message.getPayload().array()));
    }

    public void handlePongMessage(WebSocketSession session, PongMessage message) {
        logger.info(session.getRemoteAddress().getHostName() + session.getRemoteAddress().getPort());
        logger.info(new String(message.getPayload().array()));
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        super.configureInbound(messages);
        messages.simpDestMatchers("/**").authenticated();
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
