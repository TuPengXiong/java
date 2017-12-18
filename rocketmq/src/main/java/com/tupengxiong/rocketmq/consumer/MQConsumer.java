package com.tupengxiong.rocketmq.consumer;

import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;

public class MQConsumer {

	public static void run() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("PushConsumer");
		consumer.setNamesrvAddr("127.0.0.1:9876");
		consumer.setVipChannelEnabled(false);
		consumer.setInstanceName("MQConsumer");
		consumer.setPullBatchSize(10);//拉取数量
		try {
			// 订阅PushTopic下Tag为push的消息
			consumer.subscribe("PushTopic", "pull");
			// 程序第一次启动从消息队列头取数据
			//consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
						ConsumeConcurrentlyContext Context) {
					System.out.println(list.size());
					for(MessageExt messageExt:list){
						System.out.println(messageExt.toString());
						System.out.println(new String(messageExt.getBody()));
					}
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});
			consumer.start();
			System.out.println("Consumer Started.");
		} catch (Exception e) {
			e.printStackTrace();
		}/*finally {
			consumer.shutdown();
		}*/
	}
	
	public static void main(String[] args) {
		MQConsumer.run();
	}
}
