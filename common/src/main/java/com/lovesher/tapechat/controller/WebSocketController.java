package com.lovesher.tapechat.controller;

import com.lovesher.tapechat.common.MsgEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by tpx on 2017/10/8.
 */
@RestController("webSocketController")
public class WebSocketController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
     *
     * @param topic
     * @param headers
     */
    @MessageMapping("/webSocket") //"/webSocket"为WebSocketConf类中registerStompEndpoints()方法配置的
    @SendTo("/topic/greetings")
    public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers) {
        logger.info("connected successfully....");
        logger.info(topic);
        logger.info(headers.toString());
    }

    /**
     * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中，
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
     *
     * @return
     */
    @MessageMapping("/message")
    @SendToUser("/message")
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
    public UnionResp<String> send(String fromUsername, String toUsername, String content) {
        if (null == fromUsername || null == toUsername || null == content) {
            return new UnionResp(MsgEnum.Fail.getCode(), MsgEnum.Fail.getMsg(), "参数错误");
        }
        simpMessagingTemplate.convertAndSendToUser(toUsername, "/message", content);
        return new UnionResp(MsgEnum.SUCCESS.getCode(), MsgEnum.SUCCESS.getMsg(), null);
    }
}
