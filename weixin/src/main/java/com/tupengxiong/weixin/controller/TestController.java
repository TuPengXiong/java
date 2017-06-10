package com.tupengxiong.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/test")
	public String test(HttpServletResponse response) throws IOException{
		response.getWriter().write("11111111");
		response.getWriter().flush();
		return null;
	}
	
}
