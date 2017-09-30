/** 
 * Project Name:rocketmq 
 * File Name:ScheduledMessageConsumer.java 
 * Package Name:com.tupengxiong.rocketmq.consumer 
 * Date:2017年9月30日下午2:15:42 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.rocketmq.consumer;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * ClassName:ScheduledMessageConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年9月30日 下午2:15:42 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ScheduledMessageConsumer {
	public static void main(String[] args) throws Exception {
		// Instantiate message consumer
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ScheduledMessageConsumer");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		// Subscribe topics
		consumer.subscribe("TestTopic", "*");
		// Register message listener
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages,
					ConsumeConcurrentlyContext context) {
				for (MessageExt message : messages) {
					// Print approximate delay time period
					System.out.println("Receive message[msgId=" + message.getMsgId() + "] "
							+ (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		// Launch consumer
		consumer.start();
	}
}
