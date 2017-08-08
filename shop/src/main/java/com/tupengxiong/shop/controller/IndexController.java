package com.tupengxiong.shop.controller;

import com.tupengxiong.shop.common.MsgEnum;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangjunlong on 2017/8/8.
 */
@Controller
public class IndexController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map index() {
        Map map = new HashMap<String, Object>();
        map.put("code", MsgEnum.SUCCESS.getCode());
        map.put("msg", MsgEnum.SUCCESS.getMsg());
        return map;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login() {
        return "login";
    }

    @RequestMapping(value = ERROR_PATH, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map error() {
        Map map = new HashMap<String, Object>();
        map.put("code", MsgEnum.NOT_FOUND_ERROR.getCode());
        map.put("msg", MsgEnum.NOT_FOUND_ERROR.getMsg());
        return map;
    }

    @RequestMapping(value = "/loginFail", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map loginFail() {
        Map map = new HashMap<String, Object>();
        map.put("code", MsgEnum.USERNAME_NOT_FOUND_ERROR.getCode());
        map.put("msg", MsgEnum.USERNAME_NOT_FOUND_ERROR.getMsg());
        return map;
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }
}
