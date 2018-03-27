package com.aidai.job.simple;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.cloudy.fastjson.JSON;

import com.aidai.dc.loanbusiness.resposity.bean.BorrowTender;
import com.aidai.dc.loanbusiness.service.BorrowTenderService;
import com.aidai.elastic.job.SimpleJob;
import com.dangdang.ddframe.job.api.ShardingContext;

/**
 * ClassName:TaskSimpleJob <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月7日 下午5:03:00 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class TaskSimpleJob implements SimpleJob {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private BorrowTenderService borrowTenderService;

	@Override
	public void execute(ShardingContext shardingContext) {
		System.out.println("GET  shardingContext>>>>>>>>>>>>>>>>>start  "
				+ com.alibaba.fastjson.JSON.toJSONString(shardingContext));
		Random rand = new Random();
		Long randNum = (long) (rand.nextInt(923069 - 909949) + 909949);

		logger.info("randNum>>>>>>>>>" + randNum);
		BorrowTender bt = borrowTenderService.selectById(randNum);
		if (null != bt) {
			logger.info(JSON.toJSONString(bt));
		} else {
			logger.info("bt is null");
		}
		System.out.println(
				"GET  shardingContext>>>>>>>>>>>>>>>>>end  " + com.alibaba.fastjson.JSON.toJSONString(shardingContext));
	}

}
