/** 
 * Project Name:elastic-job 
 * File Name:DataflowJob.java 
 * Package Name:com.aidai.elastic.job 
 * Date:2018年3月6日下午12:53:41 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.aidai.elastic.job;

/**
 * 流式处理数据只有fetchData方法的返回值为null或集合长度为空时，作业才停止抓取，否则作业将一直运行下去； 
 * 非流式处理数据则只会在每次作业执行过程中执行一次fetchData方法和processData方法，随即完成本次作业。
 * 
 * @ClassName:  MyDataflowElasticJob   
 * @Description:流式处理数据   
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年1月27日 上午10:28:03   
 * @version V1.0
 */
public interface DataflowJob<T> extends com.dangdang.ddframe.job.api.dataflow.DataflowJob<T>{

}
  