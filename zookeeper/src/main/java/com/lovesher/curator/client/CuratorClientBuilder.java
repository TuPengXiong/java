/** 
 * Project Name:zookeeper 
 * File Name:CuratorClientFactory.java 
 * Package Name:com.lovesher.curator 
 * Date:2018年4月26日下午6:29:38 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.curator.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * ClassName:CuratorClientFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月26日 下午6:29:38 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class CuratorClientBuilder {

	
	private CuratorClientBuilder(){
		
	}
	
	public static CuratorFrameworkFactory.Builder builder(){
		return CuratorFrameworkFactory.builder();
	}
	
	public static CuratorFrameworkFactory.Builder builder(String connectString){
		return CuratorFrameworkFactory.builder().connectString(connectString);
	}
	
	public static CuratorFrameworkFactory.Builder builder(String connectString,int sessionTimeoutMs){
		return CuratorFrameworkFactory.builder().connectString(connectString).sessionTimeoutMs(sessionTimeoutMs);
	}
	
	public static CuratorFrameworkFactory.Builder builder(String connectString,int sessionTimeoutMs,int connectionTimeoutMs){
		return CuratorFrameworkFactory.builder().connectString(connectString).sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs);
	}
	
	public static CuratorFrameworkFactory.Builder builder(String connectString,int sessionTimeoutMs,int connectionTimeoutMs,int retryBaseSleepTimeMs,int retryMaxRetries){
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(retryBaseSleepTimeMs, retryMaxRetries);
		return CuratorFrameworkFactory.builder().connectString(connectString).sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs).retryPolicy(retryPolicy);
	}
	
	public static CuratorFrameworkFactory.Builder builder(String connectString,int sessionTimeoutMs,int connectionTimeoutMs,int retryBaseSleepTimeMs,int retryMaxRetries,int retryMaxSleepMs){
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(retryBaseSleepTimeMs, retryMaxRetries,retryMaxSleepMs);
		return CuratorFrameworkFactory.builder().connectString(connectString).sessionTimeoutMs(sessionTimeoutMs).connectionTimeoutMs(connectionTimeoutMs).retryPolicy(retryPolicy);
	}
}
