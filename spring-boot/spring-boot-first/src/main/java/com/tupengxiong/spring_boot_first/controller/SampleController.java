package com.tupengxiong.spring_boot_first.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * ClassName: SampleController <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年6月28日 上午10:58:43 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.8
 */
@RestController
public class SampleController {

	@Value("${name}")
	private String name;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "Hello World!" + name;
	}

	@RequestMapping("/index")
	@ResponseBody
	String index() {
		return "index";
	}
}