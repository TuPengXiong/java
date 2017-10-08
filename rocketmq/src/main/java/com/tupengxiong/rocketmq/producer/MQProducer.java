package com.tupengxiong.rocketmq.producer;


import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class MQProducer {

	public static void run(){
		DefaultMQProducer producer = new DefaultMQProducer("Producer");
		producer.setVipChannelEnabled(false);
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setInstanceName("Producer");
		try {
			producer.start();

			Message msg = new Message("PushTopic", "push", "1", "Just for test.".getBytes());

			SendResult result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			msg = new Message("PushTopic", "push", "2", "Just for test.".getBytes());

			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());

			msg = new Message("PullTopic", "pull", "1", "Just for test.".getBytes());

			result = producer.send(msg);
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}
	
	public static void main(String[] args) {
		MQProducer.run();
	}
}
