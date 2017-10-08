package com.lovesher.tapechat.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("settingInterceptor")
public class SettingInterceptor implements HandlerInterceptor {

    private static final String NET_PATH = "netPath";

    private String getPath(HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/";
        return path;
    }

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse var2, Object var3) throws Exception {
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse var2, Object var3, ModelAndView var4) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse var2, Object var3, Exception var4) throws Exception {
        httpServletRequest.setAttribute(NET_PATH, getPath(httpServletRequest));
    }
}