package com.tupengxiong.rocketmq.consumer;

import java.util.List;

import org.junit.Test;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

public class MQConsumer {

	@Test
	public void run() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
		consumer.setNamesrvAddr("106.15.93.50:9876");
		consumer.setVipChannelEnabled(false);
		consumer.setInstanceName("MQConsumer");
		try {
			// 订阅PushTopic下Tag为push的消息
			consumer.subscribe("PushTopic", "push");
			// 程序第一次启动从消息队列头取数据
			//consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
						ConsumeConcurrentlyContext Context) {
					Message msg = list.get(0);
					System.out.println(msg.toString());
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			consumer.start();
			System.out.println("Consumer Started.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
