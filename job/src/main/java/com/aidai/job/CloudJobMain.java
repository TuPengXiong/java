
package com.aidai.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aidai.dc.loanbusiness.service.BorrowTenderService;
import com.aidai.elastic.job.Job;
import com.aidai.elastic.job.LocalJob;
import com.aidai.elastic.job.bean.JobCore;
import com.aidai.job.simple.TaskSimpleJob;
import com.alibaba.fastjson.JSON;

public final class CloudJobMain {

	// CHECKSTYLE:OFF
	public static void main(final String[] args) {

		test();
		Job.run();
	}

	public static void test() {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		System.out.println(applicationContext.getBean("taskSimpleJob").getClass());
		BorrowTenderService borrowTenderService = (BorrowTenderService) applicationContext
				.getBean("borrowTenderService");
		System.out.println(JSON.toJSONString(borrowTenderService.selectById(909949L)));

		LocalJob localJob = new LocalJob();
		localJob.setJobClass(TaskSimpleJob.class);
		localJob.setBeanName("taskSimpleJob");
		localJob.setApplicationContext("applicationContext.xml");
		JobCore jobCore = new JobCore();
		jobCore.setCron("*/2 * * * * ?");
		jobCore.setJobName("taskSimpleJob");
		jobCore.setShardingItemParameters("0=Beijing,1=Shanghai,2=Guangzhou");
		jobCore.setJobParameter("dbName=AIDAI");
		jobCore.setShardingTotalCount(1);
		localJob.executeSimpleJob(jobCore);
	}
}
