/** 
 * Project Name:zookeeper 
 * File Name:CuratorClientBuilderTest.java 
 * Package Name:com.lovesher.zookeeper.curator.client 
 * Date:2018年4月27日上午10:25:45 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.zookeeper.curator.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.log4j.Logger;

import com.lovesher.curator.client.CuratorClientBuilder;

/**
 * ClassName:CuratorClientBuilderTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月27日 上午10:25:45 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class CuratorClientBuilderTest {

	private static final Logger LOGGER = Logger.getLogger(CuratorClientBuilderTest.class);

	public static void main(String[] args) {

		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final CuratorFramework client = CuratorClientBuilder.builder("127.0.0.1:2181", 10000, 10000, 1000, 3).build();
		client.start();

		final String path = "/aa";

		final ExecutorService executorService = Executors.newSingleThreadExecutor();
		/**线程池锁定节点   模拟分布式**/
		executorService.execute(new Runnable() {

			public void run() {
				InterProcessMutex lock = null;
				try {
					lock = getLock(client, path);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				} finally {
					countDownLatch.countDown();
					releaseLock(lock);
				}

			}
		});
		
		/**主线程锁定节点**/
		InterProcessMutex lock = null;
		try {
			lock = getLock(client, path);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			releaseLock(lock);
		}

		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			LOGGER.error(e.getMessage(), e);
		}
		try {
			executorService.shutdown();
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		LOGGER.info("client end");
		close(client);
	}

	private static InterProcessMutex getLock(CuratorFramework client, String path) {
		InterProcessMutex lock = new InterProcessMutex(client, path);
		try {
			boolean b = false;
			// 3秒钟获取锁 超时的话返回false
			b = lock.acquire(3L, TimeUnit.SECONDS);
			
			if(!b){
				LOGGER.error("Thread:" + Thread.currentThread().getName() +"get lock path error:" + path);
				return null;
			}
			// dobusiness
			LOGGER.info("start Thread:" + Thread.currentThread().getName() + " |get lock path:" + path);
			try {
				//这里可以设置 小于3s 或者 大于3s的时间来测试不同的结果
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
			}
			LOGGER.info("end Thread:" + Thread.currentThread().getName() + " |get lock path:" + path);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return lock;
	}

	private static void releaseLock(InterProcessMutex lock) {
		if (null != lock) {
			try {
				lock.release();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	private static void close(CuratorFramework client) {
		if (null != client) {
			try {
				client.close();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
}
