package com.tupengxiong.logistics.controller;

import com.tupengxiong.logistics.common.MsgEnum;
import com.tupengxiong.logistics.service.impl.DtSpringSecurityService;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tpx on 2017/8/8.
 */
@Controller
public class IndexController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @Resource
    private DtSpringSecurityService dtSpringSecurityService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index() {
        if (dtSpringSecurityService.getUser() == null) {
            return "login";
        } else {
            return "index";
        }
    }

    @RequestMapping(value = "/page/{html}", method = {RequestMethod.GET, RequestMethod.POST})
    public String page(@PathVariable String html) {
        if (null != html && html.endsWith(".html")) {
            return html.split(".html")[0];
        }
        return html;
    }

    @RequestMapping(value = ERROR_PATH, method = {RequestMethod.GET, RequestMethod.POST})
    public String error() {
        return "404";
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
