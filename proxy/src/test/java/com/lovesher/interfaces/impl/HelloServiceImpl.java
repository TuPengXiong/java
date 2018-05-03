package com.lovesher.interfaces.impl;

import org.springframework.stereotype.Service;

import com.lovesher.annotation.IPort;
import com.lovesher.interfaces.HelloService;

@IPort
@Service
public class HelloServiceImpl implements HelloService {
	
	@IPort
	public String sayHello(String name) {
		System.out.println(name);
		return name;
	}
}