package com.lovesher.tapechat.controller;

import com.lovesher.tapechat.common.MsgEnum;
import com.lovesher.tapechat.service.impl.DtSpringSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tpx on 2017/8/8.
 */
@RestController("indexController")
public class IndexController implements ErrorController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;

    private static final String ERROR_PATH = "/error";

    @Autowired
    private Environment env;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public void index(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (null == dtSpringSecurityService.getUser()) {
                request.getRequestDispatcher("/login").forward(request, response);
            } else {
                request.getRequestDispatcher("/chat").forward(request, response);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
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


    @RequestMapping(value = "/mail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sendSimpleEmail()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tupengxiong@qq.com");
        message.setTo("tupengxiong@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        javaMailSender.send(message);
        return "success!!";
    }
}
