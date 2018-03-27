/** 
 * Project Name:elastic-job 
 * File Name:LocalJob.java 
 * Package Name:com.aidai.elastic.job 
 * Date:2018年3月27日上午9:51:48 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.aidai.elastic.job;

import com.aidai.elastic.job.bean.JobCore;
import com.dangdang.ddframe.job.cloud.executor.local.LocalCloudJobConfiguration;
import com.dangdang.ddframe.job.cloud.executor.local.LocalTaskExecutor;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * ClassName:LocalJob <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月27日 上午9:51:48 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class LocalJob {
	
	/**
	 * 任务对应的类
	 * <p>
	 * implements com.aidai.elastic.job.SimpleJob
	 * <p>
	 * <p>
	 * or
	 * <p>
	 * <p>
	 * implements extend com.aidai.elastic.job.DataflowJob
	 * <p>
	 **/
	private Class<?> jobClass;

	/**
	 * <p>
	 * 本地任务分片数量 默认1 
	 * <p>
	 */
	private int shardingItem = 1;
	
	/**
	 * <p>
	 * spring 对应的beanName 名称
	 * <p>
	 **/
	private String beanName;

	/**
	 * <p>
	 * spring 对应的applicationContext.xml 路径
	 * <p>
	 **/
	private String applicationContext;
	
	private JobCoreConfiguration getJobCoreConfiguration(JobCore jobCore){
		Preconditions.checkArgument(null != jobCore, "jobCore can not be null.");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(jobCore.getJobName()), "jobName can not be empty.");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(jobCore.getCron()), "cron can not be empty.");
		Preconditions.checkArgument(jobCore.getShardingTotalCount() > 0, "shardingTotalCount should larger than zero.");
		JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(jobCore.getJobName(),jobCore.getCron(),jobCore.getShardingTotalCount())
										.shardingItemParameters(jobCore.getShardingItemParameters())
										.jobParameter(jobCore.getJobParameter())
										.failover(jobCore.getFailover())
										.misfire(jobCore.getMisfire())
										.description(jobCore.getDescription())
										.jobProperties("job_exception_handler", jobCore.getJob_exception_handler().getCanonicalName())
										.jobProperties("executor_service_handler", jobCore.getExecutor_service_handler().getCanonicalName()
		).build();
		return jobCoreConfiguration;
	}
	
	
	private LocalCloudJobConfiguration getSimpleLocalJobConfiguration(JobCore jobCore){
		Preconditions.checkArgument(null != this.getJobClass(), "jobClass can not be null.");
		JobCoreConfiguration jobCoreConfiguration = getJobCoreConfiguration(jobCore);
		LocalCloudJobConfiguration localCloudJobConfiguration = new LocalCloudJobConfiguration(
				new SimpleJobConfiguration(jobCoreConfiguration, this.getJobClass().getCanonicalName()),
				this.getShardingItem(),this.getBeanName(),this.getApplicationContext());
		return localCloudJobConfiguration;
	}
	
	
	private LocalCloudJobConfiguration getDataFlowJobLocalJobConfiguration(JobCore jobCore,Boolean streamingProcess){
		if(null ==  streamingProcess){
			streamingProcess = false;
		}
		Preconditions.checkArgument(null != this.getJobClass(), "jobClass can not be null.");
		JobCoreConfiguration jobCoreConfiguration = getJobCoreConfiguration(jobCore);
		LocalCloudJobConfiguration localCloudJobConfiguration = new LocalCloudJobConfiguration(
				new DataflowJobConfiguration(jobCoreConfiguration, this.getJobClass().getCanonicalName(),streamingProcess),
				this.getShardingItem(),this.getBeanName(),this.getApplicationContext());
		return localCloudJobConfiguration;
	}
	
	private LocalCloudJobConfiguration getScriptJobLocalJobConfiguration(JobCore jobCore,String scriptCommandLine){
		JobCoreConfiguration jobCoreConfiguration = getJobCoreConfiguration(jobCore);
		LocalCloudJobConfiguration localCloudJobConfiguration = new LocalCloudJobConfiguration(
				new ScriptJobConfiguration(jobCoreConfiguration,scriptCommandLine),
				this.getShardingItem(),this.getBeanName(),this.getApplicationContext());
		return localCloudJobConfiguration;
	}
	
	/**
	 * 本地模式执行 简单任务<br/> 
	 * executeSimpleJob:本地模式执行 简单任务. <br/> 
	 * @author tupengxiong
	 * @param jobCore 配置信息
	 * @since JDK 1.7
	 */
	public void executeSimpleJob(JobCore jobCore){
		new LocalTaskExecutor(getSimpleLocalJobConfiguration(jobCore)).execute();
	}
	
	/**
	 * 本地模式执行 流式任务<br/> 
	 * executeSimpleJob:本地模式执行 简单任务. <br/> 
	 * @author tupengxiong
	 * @param jobCore 配置信息
	 * @param streamingProcess 是否流式
	 * @since JDK 1.7
	 */
	public void executeDataFlowSimple(JobCore jobCore, Boolean streamingProcess){
		new LocalTaskExecutor(getDataFlowJobLocalJobConfiguration(jobCore,streamingProcess)).execute();
	}
	
	/**
	 * 本地模式执行 脚本任务<br/> 
	 * executeSimpleJob:本地模式执行 脚本任务. <br/> 
	 * @author tupengxiong
	 * @param jobCore 配置信息
	 * @param scriptCommandLine 脚本目录
	 * @since JDK 1.7
	 */
	public void executeScriptJob(JobCore jobCore,String scriptCommandLine){
		new LocalTaskExecutor(getScriptJobLocalJobConfiguration(jobCore,scriptCommandLine)).execute();
	}

	public int getShardingItem() {
		return shardingItem;
	}

	public void setShardingItem(int shardingItem) {
		this.shardingItem = shardingItem;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(String applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public Class<?> getJobClass() {
		return jobClass;
	}


	public void setJobClass(Class<?> jobClass) {
		this.jobClass = jobClass;
	}
	
	
}
