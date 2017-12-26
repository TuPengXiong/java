/** 
 * Project Name:kafka 
 * File Name:MyKafkaConsumer.java 
 * Package Name:com.lovesher.kafka 
 * Date:2017年12月26日下午5:12:50 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * ClassName:MyKafkaConsumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年12月26日 下午5:12:50 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class MyKafkaConsumer {

	private static KafkaConsumer<String,String> consumer;

	private MyKafkaConsumer() {
		Properties props = new Properties();

		// zookeeper 配置
		props.put("zookeeper.connect", "192.168.0.120:2181");

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.120:9092");
		// 消费者所在组
		props.put("group.id", "testgroup");

		// zk连接超时
		props.put("zookeeper.session.timeout.ms", "4000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "earliest");

		// 序列化类
		// 配置key的序列化类
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// 配置value的序列化类
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumer = new KafkaConsumer<String, String>(props);
	}

	void consume() {
		List<String> topicCountMap = new ArrayList<String>();
		topicCountMap.add(MyKafkaProducer.TOPIC);
		consumer.subscribe(topicCountMap);
		while (true) {
			System.out.println(consumer.poll(10L).count());
		}
	}

	public static void main(String[] args) {
		new MyKafkaConsumer().consume();
	}
}
