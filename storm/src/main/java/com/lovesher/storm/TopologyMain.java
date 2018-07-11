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
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lovesher.storm.demo.bolts.WordCounter;
import com.lovesher.storm.demo.bolts.WordNormalizer;
import com.lovesher.storm.demo.spouts.WordReader;

public class TopologyMain {

	public static void main(String[] args) throws InterruptedException {
		if (args == null || args.length == 0) {
			startDev();
		} else if (args.equals("prod")) {
			startProd();
		}
	}

	@SuppressWarnings("resource")
	public static void startDev() {
		try {

			new ClassPathXmlApplicationContext(new String[] { "/applicationContext.xml" });

			Logger.getLogger(TopologyMain.class).info("TopologyMain start.........");
			// 定义拓扑
			TopologyBuilder builder = new TopologyBuilder();
			// 数据源
			builder.setSpout("word-reader", new WordReader());
			// 数据流处理组件
			builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
			builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping("word-normalizer", new Fields("word"));

			// 配置
			Config conf = new Config();
			conf.put("wordsFile", TopologyMain.class.getResource("/test.txt").getPath());
			conf.setDebug(false);
			// 本地模式
			LocalCluster cluster = new LocalCluster();
			try {
				cluster.submitTopology("test", conf, builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Thread.sleep(15000);
			cluster.shutdown();
			Logger.getLogger(TopologyMain.class).info("TopologyMain shutdown.........");

		} catch (Exception e) {

		}
	}

	@SuppressWarnings("resource")
	public static void startProd() {
		try {

			new ClassPathXmlApplicationContext(new String[] { "/applicationContext.xml" });

			Logger.getLogger(TopologyMain.class).info("TopologyMain start.........");
			// 定义拓扑
			TopologyBuilder builder = new TopologyBuilder();
			// 数据源
			builder.setSpout("word-reader", new WordReader());
			// 数据流处理组件
			builder.setBolt("word-normalizer", new WordNormalizer()).shuffleGrouping("word-reader");
			builder.setBolt("word-counter", new WordCounter(), 2).fieldsGrouping("word-normalizer", new Fields("word"));

			// 配置
			Config conf = new Config();
			conf.put("wordsFile", TopologyMain.class.getResource("/test.txt").getPath());
			conf.setDebug(false);
			List<String> zkServers = new ArrayList<String>();
			zkServers.add("127.0.0.1");

			// 运行拓扑
			conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
			// 生产环境
			try {
				StormSubmitter.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				e.printStackTrace();
			}

			Logger.getLogger(TopologyMain.class).info("TopologyMain shutdown.........");

		} catch (Exception e) {

		}
	}
}