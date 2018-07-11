package com.lovesher.storm.demo.bolts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class WordNormalizer implements IRichBolt {
	
	private OutputCollector collector;

	public void cleanup() {
		getLogger().info("cleanup WordNormalizer");
	}

	/**
	 * bolt从单词文件接收到文本行，并标准化它。 文本行会全部转化成小写，并切分它，从中得到所有单词。
	 */
	@Override
	public void execute(Tuple input) {
		getLogger().info("execute WordNormalizer");
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for (String word : words) {
			word = word.trim();
			if (!word.isEmpty()) {
				word = word.toLowerCase();
				// 发布这个单词
				List a = new ArrayList();
				a.add(input);
				collector.emit(a, new Values(word));
			}
		}
		// 对元组做出应答
		collector.ack(input);
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		getLogger().info("prepare WordNormalizer");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		getLogger().info("declareOutputFields WordNormalizer");
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