package com.lovesher.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
/**
 * 
 * ClassName: MyKafkaProducer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason: TODO ADD REASON(可选). <br/> 
 * date: 2017年12月26日 下午5:12:57 <br/> 
 * 
 * @author tupengxiong 
 * @version  
 * @since JDK 1.7
 */
public class MyKafkaProducer {

	private final Producer<String, String> producer;
	public final static String TOPIC = "test";
	public final static String bootstrapServers = "192.168.0.120:9092";
	public final static String ACKS = "all";
	public final static String RETRIES = "1";
	private MyKafkaProducer() {

		Properties props = new Properties();
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "192.168.17.125");
		// 此处配置的是kafka的broker地址:端口列表
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		// 配置key的序列化类
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		// 配置value的序列化类
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		props.put(ProducerConfig.RETRIES_CONFIG, RETRIES);
		/**
		 * 0 不等待服务器接收 retries 设置不起作用
		 * 1 部分接收
		 * -1 all 全部接收
		 */
		props.put(ProducerConfig.ACKS_CONFIG, ACKS);

		producer = new KafkaProducer<String, String>(props);
	}

	void produce() {
		int messageNo = 1;
		final int COUNT = 101;

		int messageCount = 0;
		while (messageNo < COUNT) {
			String key = String.valueOf(messageNo);
			String data = "Hello kafka message :" + key + "192.168.17.125";
			producer.send(new ProducerRecord<String, String>(TOPIC, key, data));
			System.out.println(data);
			messageNo++;
			messageCount++;
		}

		System.out.println("Producer端一共产生了" + messageCount + "条消息！");
		
		producer.close();
	}

	public static void main(String[] args) {
		new MyKafkaProducer().produce();
	}
}