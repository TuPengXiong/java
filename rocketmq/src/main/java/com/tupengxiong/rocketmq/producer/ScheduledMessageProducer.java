/** 
 * Project Name:rocketmq 
 * File Name:ScheduledMessageProducer.java 
 * Package Name:com.tupengxiong.rocketmq.producer 
 * Date:2017年9月30日下午2:16:46 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.rocketmq.producer;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;

/**
 * ClassName:ScheduledMessageProducer <br/>
 * Function: 发送预定的消息. <br/>
 * Reason: 发送预定的消息. <br/>
 * Date: 2017年9月30日 下午2:16:46 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ScheduledMessageProducer {
	public static void main(String[] args) throws Exception {
		// Instantiate a producer to send scheduled messages
		DefaultMQProducer producer = new DefaultMQProducer("ScheduledMessageGroup");
		producer.setNamesrvAddr("127.0.0.1:9876");
		// Launch producer
		producer.start();
		int totalMessagesToSend = 100;
		for (int i = 0; i < totalMessagesToSend; i++) {
			Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
			// This message will be delivered to consumer 10 seconds later.
			message.setDelayTimeLevel(3);
			// Send the message
			producer.send(message);
		}

		// Shutdown producer after use.
		producer.shutdown();
	}
}
