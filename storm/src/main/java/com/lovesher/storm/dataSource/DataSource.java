/** 
 * Project Name:storm 
 * File Name:DataSource.java 
 * Package Name:com.lovesher.storm.dataSource 
 * Date:2018年7月11日下午9:07:30 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.storm.dataSource;

import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import com.lovesher.storm.enums.DataSourceMethodEnum;

/** 
 * ClassName:DataSource <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年7月11日 下午9:07:30 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
@SuppressWarnings("serial")
public abstract class DataSource<T> implements IRichSpout {
	

	private TopologyContext context = null;
	
	private SpoutOutputCollector collector;
	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.context = context;
		this.collector = collector;
	}

	@Override
	public void close() {
		
	}

	@Override
	public void activate() {
	}

	@Override
	public void deactivate() {
	}

	@Override
	public void nextTuple() {
		try{
			collector.emit((List<Object>) getData());
		}catch(Throwable e){
			onError(context, collector, DataSourceMethodEnum.nextTuple,e);
		}
	}

	@Override
	public void ack(Object msgId) {
		
	}

	@Override
	public void fail(Object msgId) {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		try{
			declarer.declare(new Fields(getFields()));
		}catch(Throwable e){
			onError(context, collector, DataSourceMethodEnum.declareOutputFields,e);
		}
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		
		return null;
	}

	public abstract List<Object>  getData();
	
	public abstract List<String>  getFields();
	
	public abstract void onError(TopologyContext context, SpoutOutputCollector collector,
			DataSourceMethodEnum dataSourceMethod, Throwable e);

	public TopologyContext getContext() {
		return context;
	}

	public SpoutOutputCollector getCollector() {
		return collector;
	}
}
  