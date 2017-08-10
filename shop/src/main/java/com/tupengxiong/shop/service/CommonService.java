package com.tupengxiong.shop.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CommonService {

    public String getPath(HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath() + "/";
        return path;
    }
}