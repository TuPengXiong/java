package com.tupengxiong.rocketmq.producer;


import com.alibaba.fastjson.JSON;
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
			for(int i=0;i<10;i++){
				Message msg = new Message("PushTopic", "pull", "1", ("content"+i).getBytes());
				SendResult result = producer.send(msg);
				System.out.println(JSON.toJSONString(result));
			}
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
