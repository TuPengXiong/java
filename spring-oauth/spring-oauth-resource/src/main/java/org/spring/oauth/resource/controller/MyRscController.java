package org.spring.oauth.resource.controller;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
class MyRscController {

	// 模拟图片信息
	private Map photos = new HashMap<String, String>();

	@RequestMapping("/")
	@ResponseBody
	@PreAuthorize("permitAll")
	String index(@AuthenticationPrincipal Object curUser) {
		return "Hello World!~ " + curUser + " @ " + new Date();
	}

	// --------------------------------------------- 人员登录后的功能
	@RequestMapping("/sec")
	@ResponseBody
	@PreAuthorize("permitAll")
	String sec() {
		return "sec " + new Date();
	}

	// --------------------------------------------- resource server

	/** 获取所有图片列表 */
    @RequestMapping("/o2/photo")
    @ResponseBody
    // OAuth2MethodSecurityExpressionHandler, OAuth2SecurityExpressionMethods, OAuth2Authentication
    @PreAuthorize("#oauth2.hasScope('read')")
    Object photos(Principal principal) {
        return photos;
    }
}