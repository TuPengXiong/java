package com.lovesher.storm.spouts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

@SuppressWarnings("serial")
public class WordReader implements IRichSpout {
	
	private SpoutOutputCollector collector;
	private FileReader fileReader;
	private boolean completed = false;
	private TopologyContext context;

	public boolean isDistributed() {
		return false;
	}

	@Override
	public void ack(Object msgId) {
		getLogger().info("ack WordReader");
		getLogger().info("OK:" + msgId);
	}

	@Override
	public void close() {
		getLogger().info("close WordReader");
	}

	@Override
	public void fail(Object msgId) {
		getLogger().info("fail WordReader");
		getLogger().info("FAIL:" + msgId);
	}

	/**
	 * 这个方法做的惟一一件事情就是分发文件中的文本行
	 */
	@Override
	public void nextTuple() {
		getLogger().info("nextTuple WordReader");
		/**
		 * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
		 */
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// 什么也不做
			}
			return;
		}
		String str;
		// 创建reader
		BufferedReader reader = new BufferedReader(fileReader);
		try {
			// 读所有文本行
			while ((str = reader.readLine()) != null) {
				/**
				 * 按行发布一个新值
				 */
				this.collector.emit(new Values(str), str);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error reading tuple", e);
		} finally {
			completed = true;
		}
	}

	/**
	 * 我们将创建一个文件并维持一个collector对象
	 */
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		getLogger().info("open WordReader");
		try {
			this.context = context;
			this.fileReader = new FileReader(conf.get("wordsFile").toString());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Error reading file [" + conf.get("wordFile") + "]");
		}
		this.collector = collector;
	}

	/**
	 * 声明输入域"word"
	 */
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		getLogger().info("declareOutputFields WordReader");
		declarer.declare(new Fields("line"));
	}

	@Override
	public void activate() {
		getLogger().info("activate WordReader");
	}

	@Override
	public void deactivate() {
		getLogger().info("deactivate WordReader");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	private Logger getLogger(){
		return Logger.getLogger(getClass());
	}
}