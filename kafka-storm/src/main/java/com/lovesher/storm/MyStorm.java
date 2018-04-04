package com.lovesher.storm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.MultiScheme;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import com.lovesher.storm.bolt.MyBolt;


/**
 * ClassName:MyStorm <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月2日 下午8:06:40 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class MyStorm {

	private static String hostName = "tupx.com";
	private static int zkPort = 2181;
	private static int kafkaPort = 9092;

	public static void main(String[] args) {
		BrokerHosts brokerHosts = new ZkHosts(hostName + ":" + zkPort);
		// SpoutConfig继承KafkaConfig接口并有序列化，默认是60秒向ZK写入offset（可查看Zkhosts源码)
		SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "test-toptc", "/zkkafkaspout", "kafkaspout");
		spoutConfig.zkPort = zkPort;
		List<String> zkServers = new ArrayList<String>();
		zkServers.add(hostName);
		spoutConfig.zkServers = zkServers;
		// 来自Storm-core的方法
		MultiScheme multiScheme = new SchemeAsMultiScheme(new StringScheme());
		spoutConfig.scheme = multiScheme;
		
		Config config = new Config();
		Map<String, String> map = new HashMap<String, String>();
		map.put("metadata.broker.list", hostName + ":" + kafkaPort);
		config.put("kafka.broker.properties", map);
		// SchemeAsMultiScheme实现了 MultiScheme 接口，构造方法参数为Scheme

		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("kafka-reader", new KafkaSpout(spoutConfig), 5); // Kafka我们创建了一个5分区的Topic，这里并行度设置为5
		builder.setBolt("my-bolt", new MyBolt(), 2).shuffleGrouping("kafka-reader");
		String name = MyStorm.class.getSimpleName();
		config.setMaxTaskParallelism(3);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(name, config, builder.createTopology());
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//cluster.shutdown();
	}
}
