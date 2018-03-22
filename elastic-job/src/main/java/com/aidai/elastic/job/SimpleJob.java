/** 
 * Project Name:elastic-job 
 * File Name:SimpleJob.java 
 * Package Name:com.aidai.elastic.job 
 * Date:2018年3月6日下午12:48:38 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.aidai.elastic.job;  
/**
 * 简单实现，未经任何封装的类型。需实现SimpleJob接口。该接口仅提供单一方法用于覆盖，此方法将定时执行。
 * 与Quartz原生接口相似，但提供了弹性扩缩容和分片等功能。
 * @ClassName:  MySimpleElasticJob   
 * @Description:简单实现 
 * @author: tupengxiong tupengxiong@qq.com
 * @date:   2018年1月27日 上午10:28:08   
 * @version V1.0
 */
public interface SimpleJob extends com.dangdang.ddframe.job.api.simple.SimpleJob{

}
  