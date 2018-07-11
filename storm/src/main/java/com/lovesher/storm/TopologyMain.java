package com.lovesher.storm;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import com.lovesher.storm.bolts.WordCounter;
import com.lovesher.storm.bolts.WordNormalizer;
import com.lovesher.storm.spouts.WordReader;

public class TopologyMain {
	public static void main(String[] args) throws InterruptedException {
		Logger.getLogger(TopologyMain.class).info("TopologyMain start.........");
		// 定义拓扑
		TopologyBuilder builder = new TopologyBuilder();
		//数据源
		builder.setSpout("word-reader", new WordReader());
		//数据流处理组件
		builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping("word-normalizer", new Fields("word"));

		// 配置
		Config conf = new Config();
		conf.put("wordsFile", TopologyMain.class.getResource("/test.txt").getPath());
		conf.setDebug(false);
		/*List<String> zkServers = new ArrayList<String>();
		zkServers.add("127.0.0.1");
		conf.put(Config.STORM_ZOOKEEPER_SERVERS, zkServers);
		conf.put(Config.STORM_ZOOKEEPER_PORT, 2181);
		conf.put(Config.STORM_LOCAL_HOSTNAME, "127.0.0.1");*/

		// 运行拓扑
		//conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
		//本地模式
		LocalCluster cluster = new LocalCluster();
		try{
			cluster.submitTopology("test", conf, builder.createTopology());
		}catch(Exception e){
			e.printStackTrace();
		}
		Thread.sleep(15000);
		cluster.shutdown();
		//生产环境
		/*try {
			StormSubmitter.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
		} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
			e.printStackTrace();
		}*/
		
		Logger.getLogger(TopologyMain.class).info("TopologyMain shutdown.........");
	}
}