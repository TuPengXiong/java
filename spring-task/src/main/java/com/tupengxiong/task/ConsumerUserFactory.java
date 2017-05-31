package com.tupengxiong.task;

public class ConsumerUserFactory {
	
	public static Consumer<?> newConsumer(Object dto, Producer<?> thread) {
		if(dto instanceof String) {
			return new ConsumerApp((String)dto, thread);
		}
		
		
		return null;
	}

}
