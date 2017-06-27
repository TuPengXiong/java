package com.tupengxiong.spring_boot_first.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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