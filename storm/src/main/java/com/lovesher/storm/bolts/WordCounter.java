package com.lovesher.storm.bolts;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class WordCounter implements IRichBolt {
	
	private Integer id;
	private String name;
	private Map<String, Integer> counters;
	private OutputCollector collector;

	/**
	 * 这个spout结束时（集群关闭的时候），我们会显示单词数量
	 */
	@Override
	public void cleanup() {
		getLogger().info("-- 单词数 【" + name + "-" + id + "】 --");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			getLogger().info(entry.getKey() + ": " + entry.getValue());
		}
	}

	/**
	 * 为每个单词计数
	 */
	@Override
	public void execute(Tuple input) {
		getLogger().info("execute word");
		String str = input.getString(0);
		/**
		 * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		 */
		if (!counters.containsKey(str)) {
			counters.put(str, 1);
		} else {
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
		// 对元组作为应答
		collector.ack(input);
	}

	/**
	 * 初始化
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
		getLogger().info("prepare word");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		getLogger().info("declarer word");
		declarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	private Logger getLogger(){
		return Logger.getLogger(getClass());
	}
}