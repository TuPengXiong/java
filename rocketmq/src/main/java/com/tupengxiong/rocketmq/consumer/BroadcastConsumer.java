/** 
 * Project Name:rocketmq 
 * File Name:BroadcastConsumer.java 
 * Package Name:com.tupengxiong.rocketmq.consumer 
 * Date:2017年9月30日下午2:12:06 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.rocketmq.consumer;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * ClassName:BroadcastConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年9月30日 下午2:12:06 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class BroadcastConsumer {
	public static void main(String[] args) throws Exception {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("BroadcastConsumer");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		// set to broadcast mode
		consumer.setMessageModel(MessageModel.BROADCASTING);

		consumer.subscribe("TopicTest", "TagA || TagC || TagD");

		consumer.registerMessageListener(new MessageListenerConcurrently() {

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});

		consumer.start();
		System.out.printf("Broadcast Consumer Started.%n");
	}
}
