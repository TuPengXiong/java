/** 
 * Project Name:kafka 
 * File Name:MyCallback.java 
 * Package Name:com.lovesher.kafka 
 * Date:2017年12月26日下午5:50:13 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

/** 
 * ClassName:MyCallback <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年12月26日 下午5:50:13 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class MyCallback implements Callback{

	public void onCompletion(RecordMetadata metadata, Exception exception) {
		
		System.out.println(metadata.toString());
		System.out.println(exception.getMessage());
	}
}
  