package com.lovesher.tapechat.controller;

import com.lovesher.tapechat.common.MsgEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by tpx on 2017/8/8.
 */
@RestController("indexController")
public class IndexController implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public UnionResp index() {
        return new UnionResp(MsgEnum.SUCCESS.getCode(), MsgEnum.SUCCESS.getMsg(), null);
    }

    @RequestMapping(value = ERROR_PATH, method = {RequestMethod.GET, RequestMethod.POST})
    public UnionResp error() {
        return new UnionResp(MsgEnum.NOT_FOUND_ERROR.getCode(), MsgEnum.NOT_FOUND_ERROR.getMsg(), null);
    }

    @RequestMapping(value = "/loginFail", method = {RequestMethod.GET, RequestMethod.POST})
    public UnionResp loginFail() {
        return new UnionResp(MsgEnum.USERNAME_NOT_FOUND_ERROR.getCode(), MsgEnum.USERNAME_NOT_FOUND_ERROR.getMsg(), null);
    }
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
