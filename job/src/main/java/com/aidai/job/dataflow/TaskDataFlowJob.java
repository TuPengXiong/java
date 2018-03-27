package com.aidai.job.dataflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aidai.elastic.job.DataflowJob;
import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * ClassName:TaskDataFlow <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月7日 下午4:51:33 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class TaskDataFlowJob implements DataflowJob<String> {

	@Override
	public List<String> fetchData(ShardingContext shardingContext) {
		System.out.println("fetchData " + System.currentTimeMillis());
		List<String> lists = new ArrayList<String>();
		lists.add(JSON.toJSONString(shardingContext));
		return lists;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<String> data) {
		System.out.println("processData " + System.currentTimeMillis());
		System.out.println(JSON.toJSONString(data));
		System.out.println(JSON.toJSONString(shardingContext));
	}

}
