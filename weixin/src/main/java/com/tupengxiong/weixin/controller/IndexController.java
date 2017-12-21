package com.tupengxiong.weixin.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("IndexController")
public class IndexController {

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping("/index")
	public @ResponseBody String index(){
		String index = "3333";
		logger.info(index);
		return index;
	}
}
