/** 
 * Project Name:kafka-storm 
 * File Name:MyBolt.java 
 * Package Name:com.lovesher.storm.bolt 
 * Date:2018年4月2日下午4:19:36 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.storm.bolt;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/** 
 * ClassName:MyBolt <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年4月2日 下午4:19:36 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
@SuppressWarnings("serial")
public class MyBolt extends BaseRichBolt{

	private OutputCollector collector;
	/**
	 * 初始化工作
	 * TODO 初始化工作
	 * @see org.apache.storm.task.IBolt#prepare(java.util.Map, org.apache.storm.task.TopologyContext, org.apache.storm.task.OutputCollector)
	 */
	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		 this.collector = collector;
	}

	/**
	 * 执行逻辑
	 * @see org.apache.storm.task.IBolt#execute(org.apache.storm.tuple.Tuple)
	 */
	public void execute(Tuple input) {
		
		 String str = input.getString(0);
	        if(StringUtils.isNotBlank(str)){
	            String [] lines = str.split("\n");
	            for(String line : lines){
	                if(StringUtils.isBlank(line) || line.charAt(0) == '#'){
	                    continue;
	                }
	                //发射到下一个bolt
	                collector.emit(new Values(line));
	            }
	            //汇报给storm,该tuple执行成功
	            collector.ack(input);
	        }else{
	            //执行失败
	            collector.fail(input);
	        }
	}

	/**
	 * 申明传入到一个Bolt的字段名称
	 * @see org.apache.storm.topology.IComponent#declareOutputFields(org.apache.storm.topology.OutputFieldsDeclarer)
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
	}


}
  