/** 
 * Project Name:jvm 
 * File Name:ThreadPoolExecutor.java 
 * Package Name:com.tupengxiong.jvm 
 * Date:2017年11月3日下午1:41:49 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName:ThreadPoolExecutor <br/>
 * Date: 2017年11月3日 下午1:41:49 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see Java通过Executors提供四种线程池，分别为：
 *      newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 *      newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 *      newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
 *      newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class ThreadPoolExecutorAnaly {
	// 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
	static ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
	// 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
	static ExecutorService fixedExecutorService = Executors.newFixedThreadPool(3);
	// 单线程池，相当于FixedThreadPool(1)
	static ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
	// 创建一个定长线程池，支持定时及周期性任务执行
	static ExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

	public static void testCachedExecutorService() {
		cachedExecutorService.execute(new Runnable() {

			@Override
			public void run() {

				System.out.println("cachedExecutorService");
			}
		});
		cachedExecutorService.shutdown();
		
		
	}

	public static void testSingleExecutorService() {
		singleExecutorService.execute(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				System.out.println("testSingleExecutorService");
			}
		});
		singleExecutorService.shutdown();
	}

	public static void testFixedExecutorService(final int i) {
		fixedExecutorService.execute(new Runnable() {

			@Override
			public void run() {

				System.out.println("testFixedExecutorService"+i);
			}
		});
	}

	public static void testScheduledExecutorService() {
		scheduledExecutorService.execute(new Runnable() {

			@Override
			public void run() {

				System.out.println("testScheduledExecutorService");
			}
		});
		scheduledExecutorService.shutdown();
	}

	public static void main(String[] args) {
		testCachedExecutorService();
		testSingleExecutorService();
		for(int i=0;i<10;i++){
			testFixedExecutorService(i);
		}
		testScheduledExecutorService();
	}
}
