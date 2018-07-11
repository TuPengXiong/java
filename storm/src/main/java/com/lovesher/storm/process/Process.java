/** 
 * Project Name:storm 
 * File Name:Process.java 
 * Package Name:com.lovesher.storm.process 
 * Date:2018年7月11日下午9:38:56 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.storm.process;

import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import com.lovesher.storm.enums.ProcessMethodEnum;

/**
 * ClassName:Process <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年7月11日 下午9:38:56 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@SuppressWarnings("serial")
public abstract class Process implements IRichBolt {

	private OutputCollector collector;
	private boolean completed = false;
	private TopologyContext context;
	private Integer id;
	private String name;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void execute(Tuple input) {
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		try {
			declarer.declare(new Fields(getFields()));
		} catch (Throwable e) {
			onError(context, collector, ProcessMethodEnum.declareOutputFields, e);
		}
	}

	public abstract void onError(TopologyContext context, OutputCollector collector,
			ProcessMethodEnum processMethodEnum, Throwable e);

	@Override
	public Map<String, Object> getComponentConfiguration() {

		return null;
	}

	public abstract List<Object> getData();

	public abstract List<String> getFields();

	public OutputCollector getCollector() {
		return collector;
	}

	public boolean isCompleted() {
		return completed;
	}

	public TopologyContext getContext() {
		return context;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
