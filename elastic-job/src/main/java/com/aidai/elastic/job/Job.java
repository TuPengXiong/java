/** 
 * Project Name:elastic-job 
 * File Name:Job.java 
 * Package Name:com.aidai.elastic.job 
 * Date:2018年3月6日下午1:03:27 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.aidai.elastic.job;

import com.dangdang.ddframe.job.cloud.api.JobBootstrap;

/** 
 * job 启动类
 * ClassName:Job <br/> 
 * Function: job 启动类
 * Reason:   job 启动类
 * Date:     2018年3月6日 下午1:03:27 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class Job {
	public static void run() {
		JobBootstrap.execute();
	}
}
  