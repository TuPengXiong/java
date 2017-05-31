package com.tupengxiong.task;

import org.springframework.stereotype.Component;

@Component
public class ProducerApp extends Producer<String>{

	public ProducerApp(){
		super("ProducerApp");
	}
	protected ProducerApp(String name) {
		super(name);
	}

	@Override
	public int fill() {
		System.out.println("ProducerApp.fill running");
		return 0;
	}

}
