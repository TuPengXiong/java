package com.lovesher.tapechat.controller;

import com.lovesher.tapechat.beans.Message;
import com.lovesher.tapechat.beans.User;
import com.lovesher.tapechat.common.MessageType;
import com.lovesher.tapechat.common.MsgEnum;
import com.lovesher.tapechat.dao.MessageDao;
import com.lovesher.tapechat.service.impl.DtSpringSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;
import java.util.Map;

/**
 * Created by tpx on 2017/10/8.
 */
@RestController("webSocketController")
@RequestMapping("/api/webSocket")
public class WebSocketController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;

    @Resource
    private MessageDao messageDao;

    /**
     * 表示服务端可以接收客户端通过主题“/app/webSocket”发送过来的消息，客户端需要在主题"/topic/greetings"上监听并接收服务端发回的消息
     *
     * @param topic
     * @param headers
     */
    @MessageMapping("/webSocket") //"/webSocket"为WebSocketConf类中registerStompEndpoints()方法配置的
    @SendTo("/topic/greetings")
    @PreAuthorize("isAuthenticated()")
    public UnionResp<Message> greeting(Principal principal, @Header("atytopic") String topic, @Headers Map<String, Object> headers, @Header("msg") String msg, @Header("msgType") String msgType) {
        logger.info(principal.getName());
        logger.info("connected successfully....");
        logger.info(topic);
        logger.info(headers.toString());
        User user = dtSpringSecurityService.getUser();
        Message message = new Message();
        message.setCreateTime(new Date());
        message.setModifyTime(new Date());
        message.setFromUserId(user.getId());
        message.setFromUsername(user.getUsername());
        message.setType(MessageType.valueOf(msgType) == null ? MessageType.MESSAGE.getType() : MessageType.valueOf(msgType).getType());
        message.setMsg(msg);
        message.setToUserId(null);
        message.setToUsername("/topic/greetings");
        message.setPhotoUrl(user.getPhotoUrl() == null ? "images/head_portrait.png" : user.getPhotoUrl());
        messageDao.save(message);
        return new UnionResp<Message>(MsgEnum.SUCCESS.getCode(), MsgEnum.SUCCESS.getMsg(), message);
    }

    /**
     * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中，
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
     *
     * @return
     */
    @MessageMapping("/message")
    @SendToUser("/message")
    @PreAuthorize("isAuthenticated()")
    public UnionResp<String> handleSubscribe() {
        logger.info("handleSubscribe");
        return new UnionResp(MsgEnum.SUCCESS.getCode(), MsgEnum.SUCCESS.getMsg(), MsgEnum.SUCCESS.getMsg());
    }

    /**
     * 测试对指定用户发送消息方法
     *
     * @return
     */
    @RequestMapping(path = "/send", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public UnionResp<String> send(String fromUsername, String toUsername, String content) {
        if (null == fromUsername || null == toUsername || null == content) {
            return new UnionResp(MsgEnum.Fail.getCode(), MsgEnum.Fail.getMsg(), "参数错误");
        }
        simpMessagingTemplate.convertAndSendToUser(toUsername, "/message", content);
        return new UnionResp(MsgEnum.SUCCESS.getCode(), MsgEnum.SUCCESS.getMsg(), null);
    }
}
