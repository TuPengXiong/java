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

	private MyKafkaProducer() {

		Properties props = new Properties();
		props.put("zookeeper.connect", "192.168.0.120:2181");
		// 此处配置的是kafka的broker地址:端口列表
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.120:9092");
		// 配置key的序列化类
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		// 配置value的序列化类
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		// request.required.acks
		// 0, which means that the producer never waits for an acknowledgement
		// from the broker (the same behavior as 0.7). This option provides the
		// lowest latency but the weakest durability guarantees (some data will
		// be lost when a server fails).
		// 1, which means that the producer gets an acknowledgement after the
		// leader replica has received the data. This option provides better
		// durability as the client waits until the server acknowledges the
		// request as successful (only messages that were written to the
		// now-dead leader but not yet replicated will be lost).
		// -1, which means that the producer gets an acknowledgement after all
		// in-sync replicas have received the data. This option provides the
		// best durability, we guarantee that no messages will be lost as long
		// as at least one in sync replica remains.
		props.put(ProducerConfig.ACKS_CONFIG, "0");

		producer = new KafkaProducer<String, String>(props);
	}

	void produce() {
		int messageNo = 1+1000;
		final int COUNT = 101+messageNo;

		int messageCount = 0;
		while (messageNo < COUNT) {
			String key = String.valueOf(messageNo);
			String data = "Hello kafka message :" + key;
			producer.send(new ProducerRecord<String, String>(TOPIC, key, data));
			System.out.println(data);
			messageNo++;
			messageCount++;
		}

		System.out.println("Producer端一共产生了" + messageCount + "条消息！");
	}

	public static void main(String[] args) {
		new MyKafkaProducer().produce();
	}
}