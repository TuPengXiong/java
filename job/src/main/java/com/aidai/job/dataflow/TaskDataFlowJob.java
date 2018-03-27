package com.aidai.job.dataflow;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aidai.dc.loanbusiness.bean.BorrowTenderDTO;
import com.aidai.dc.loanbusiness.resposity.bean.BorrowTender;
import com.aidai.dc.loanbusiness.service.BorrowTenderService;
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
public class TaskDataFlowJob implements DataflowJob<BorrowTender> {

	private Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private BorrowTenderService borrowTenderService;
	
	@Override
	public List<BorrowTender> fetchData(ShardingContext shardingContext) {
		logger.info("fetchData------------ " + System.currentTimeMillis());
		List<BorrowTender> lists = new ArrayList<BorrowTender>();
		Random rand = new Random();
		Long randNum = (long) (rand.nextInt(923069 - 909949) + 909949);
		BorrowTender bt = borrowTenderService.selectById(randNum);
		if (null != bt) {
			lists.add(bt);
		}
		return lists;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<BorrowTender> data) {
		logger.info("processData------------ " + System.currentTimeMillis());
		logger.info(JSON.toJSONString(shardingContext));
		if(data == null || data.isEmpty()){
			logger.info("data is empty");
		}else{
			for(BorrowTender borrowTender:data){
				logger.info("processData id---------- "+borrowTender.getId());
				BorrowTenderDTO record = new BorrowTenderDTO();
				record.setId(borrowTender.getId());
				record.setModifytime(new Timestamp(System.currentTimeMillis()));
				logger.info("update id:" + borrowTender.getId() + "-----result:" + borrowTenderService.update(record));
			}
		}
	}

}
