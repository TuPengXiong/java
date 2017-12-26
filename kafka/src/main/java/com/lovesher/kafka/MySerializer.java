/** 
 * Project Name:kafka 
 * File Name:MySerializer.java 
 * Package Name:com.lovesher.kafka 
 * Date:2017年12月26日下午5:34:03 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

/** 
 * ClassName:MySerializer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年12月26日 下午5:34:03 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class MySerializer implements Serializer<String>{

	public void configure(Map<String, ?> configs, boolean isKey) {
		System.out.println("------------configure------------------");
		System.out.println(configs);
		System.out.println(isKey);
	}

	public void close() {
		try {
			super.clone();
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
	}

	public byte[] serialize(String topic, String data) {
		System.out.println("------------serialize------------------");
		System.out.println(topic);
		System.out.println(data);
		return (topic.getBytes()+"|" +data).getBytes();
	}

}
  