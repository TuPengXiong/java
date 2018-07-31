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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
 *      newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。 newSingleThreadExecutor
 *      创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 */
public class ThreadPoolExecutorAnaly {
	// 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
	static ExecutorService cachedExecutorService = Executors.newCachedThreadPool();
	// 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
	static ExecutorService fixedExecutorService = Executors.newFixedThreadPool(3);
	// 单线程池，相当于FixedThreadPool(1)
	static ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();
	// 创建一个定长线程池，支持定时及周期性任务执行
	static ExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

	private int poolSize = 1;

	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutorAnaly analy = new ThreadPoolExecutorAnaly();
		Long poolSize = (long) (1 << 4);

		//testcachedExecutor(analy, poolSize);

		//testfixedExecutor(analy, poolSize);
		testsingleExecutor(analy, poolSize);
		testscheduledExecutor(analy, poolSize);
	}

	/**
	 * 多次执行 获取到的结果
		pool-1-thread-1>>>MyTask2>>2
		testcachedExecutor start1.......................................
		end.......................................
		pool-1-thread-5>>>MyTask2>>4
		end.......................................
		pool-1-thread-4>>>MyTask2>>5
		end.......................................
		pool-1-thread-6>>>MyTask2>>6
		end.......................................
		pool-1-thread-7>>>MyTask2>>7
		end.......................................
		pool-1-thread-8>>>MyTaskSync2>>8
		end.........
		pool-1-thread-9>>>MyTaskSync2>>9
		end.........
		pool-1-thread-10>>>MyTaskSync2>>10
		end.........
		pool-1-thread-11>>>MyTaskSync2>>11
		end.........
		pool-1-thread-12>>>MyTaskSync2>>12
		end.........
		pool-1-thread-13>>>MyTaskSync2>>13
		end.........
		pool-1-thread-14>>>MyTaskSync2>>14
		end.........
		pool-1-thread-2>>>MyTask2>>3
		end.......................................
		pool-1-thread-3>>>MyTask2>>15
		end.......................................
	 * @author tupengxiong
	 * @param analy
	 * @param poolSize
	 * @throws InterruptedException 
	 * @since JDK 1.7
	 */
	public static void testcachedExecutor(ThreadPoolExecutorAnaly analy, Long poolSize) throws InterruptedException {
		cachedExecutorService.execute(new MyTask(analy));
		cachedExecutorService.execute(new MyTask(analy));
		cachedExecutorService.execute(new MyTask(analy));
		cachedExecutorService.execute(new MyTask(analy));
		cachedExecutorService.execute(new MyTask(analy));
		cachedExecutorService.execute(new MyTask(analy));
		cachedExecutorService.execute(new MyTask(analy));

		System.out.println("testcachedExecutor start1.......................................");
		cachedExecutorService.execute(new MyTaskSync(analy));
		cachedExecutorService.execute(new MyTaskSync(analy));
		cachedExecutorService.execute(new MyTaskSync(analy));
		cachedExecutorService.execute(new MyTaskSync(analy));
		cachedExecutorService.execute(new MyTaskSync(analy));
		cachedExecutorService.execute(new MyTaskSync(analy));
		cachedExecutorService.execute(new MyTaskSync(analy));
		
		cachedExecutorService.shutdown();
	}

	
	/**
	 * 多次执行 获取到的结果
	 * fixedExecutorService start1.......................................
		pool-2-thread-2>>>MyTask2>>3
		end.......................................
		pool-2-thread-2>>>MyTask2>>4
		end.......................................
		pool-2-thread-2>>>MyTask2>>5
		end.......................................
		pool-2-thread-2>>>MyTask2>>6
		end.......................................
		pool-2-thread-2>>>MyTask2>>7
		end.......................................
		pool-2-thread-1>>>MyTask2>>2
		end.......................................
		pool-2-thread-3>>>MyTask2>>8
		pool-2-thread-1>>>MyTaskSync2>>10
		end.........
		pool-2-thread-1>>>MyTaskSync2>>11
		end.........
		pool-2-thread-1>>>MyTaskSync2>>12
		pool-2-thread-2>>>MyTaskSync2>>9
		end.........
		pool-2-thread-2>>>MyTaskSync2>>13
		end.........
		pool-2-thread-2>>>MyTaskSync2>>14
		end.........
		pool-2-thread-2>>>MyTaskSync2>>15
		end.........
		end.........
		end.......................................
	 * @author tupengxiong
	 * @param analy
	 * @param poolSize
	 * @throws InterruptedException 
	 * @since JDK 1.7
	 */
	public static void testfixedExecutor(ThreadPoolExecutorAnaly analy, Long poolSize) throws InterruptedException {
		// 超出的线程会在队列中等待
		
		fixedExecutorService.execute(new MyTask(analy));
		fixedExecutorService.execute(new MyTask(analy));
		fixedExecutorService.execute(new MyTask(analy));
		fixedExecutorService.execute(new MyTask(analy));
		fixedExecutorService.execute(new MyTask(analy));
		fixedExecutorService.execute(new MyTask(analy));
		fixedExecutorService.execute(new MyTask(analy));

		System.out.println("fixedExecutorService start1.......................................");
		fixedExecutorService.execute(new MyTaskSync(analy));
		fixedExecutorService.execute(new MyTaskSync(analy));
		fixedExecutorService.execute(new MyTaskSync(analy));
		fixedExecutorService.execute(new MyTaskSync(analy));
		fixedExecutorService.execute(new MyTaskSync(analy));
		fixedExecutorService.execute(new MyTaskSync(analy));
		fixedExecutorService.execute(new MyTaskSync(analy));
		
		fixedExecutorService.shutdown();
	}

	/**
	 *  多次执行
	 * fixedExecutorService start1.......................................
		pool-3-thread-1>>>MyTask2>>2
		end.......................................
		pool-3-thread-1>>>MyTask2>>3
		end.......................................
		pool-3-thread-1>>>MyTask2>>4
		end.......................................
		pool-3-thread-1>>>MyTask2>>5
		end.......................................
		pool-3-thread-1>>>MyTask2>>6
		end.......................................
		pool-3-thread-1>>>MyTask2>>7
		end.......................................
		pool-3-thread-1>>>MyTask2>>8
		end.......................................
		pool-3-thread-1>>>MyTaskSync2>>9
		end.........
		pool-3-thread-1>>>MyTaskSync2>>10
		end.........
		pool-3-thread-1>>>MyTaskSync2>>11
		end.........
		pool-3-thread-1>>>MyTaskSync2>>12
		end.........
		pool-3-thread-1>>>MyTaskSync2>>13
		end.........
		pool-3-thread-1>>>MyTaskSync2>>14
		end.........
		pool-3-thread-1>>>MyTaskSync2>>15
		end.........
	 * @author tupengxiong
	 * @param analy
	 * @param poolSize
	 * @throws InterruptedException 
	 * @since JDK 1.7
	 */
	public static void testsingleExecutor(ThreadPoolExecutorAnaly analy, Long poolSize) throws InterruptedException {
		// 单线程顺序执行
		singleExecutorService.execute(new MyTask(analy));
		singleExecutorService.execute(new MyTask(analy));
		singleExecutorService.execute(new MyTask(analy));
		singleExecutorService.execute(new MyTask(analy));
		singleExecutorService.execute(new MyTask(analy));
		singleExecutorService.execute(new MyTask(analy));
		singleExecutorService.execute(new MyTask(analy));

		System.out.println("fixedExecutorService start1.......................................");
		singleExecutorService.execute(new MyTaskSync(analy));
		singleExecutorService.execute(new MyTaskSync(analy));
		singleExecutorService.execute(new MyTaskSync(analy));
		singleExecutorService.execute(new MyTaskSync(analy));
		singleExecutorService.execute(new MyTaskSync(analy));
		singleExecutorService.execute(new MyTaskSync(analy));
		singleExecutorService.execute(new MyTaskSync(analy));
		
		singleExecutorService.shutdown();
	}

	public static void testscheduledExecutor(ThreadPoolExecutorAnaly analy, Long poolSize) throws InterruptedException {
		ScheduledExecutorService scheduledExecutor = (ScheduledExecutorService) scheduledExecutorService;
		// 只执行一次
		scheduledExecutor.schedule(new MyTask(analy), 0, TimeUnit.SECONDS);
		// 周期性执行，每5秒执行一次
		scheduledExecutor.scheduleAtFixedRate(new MyTask(analy), 0, 5, TimeUnit.SECONDS);
		//scheduledExecutor.shutdown();
	}

	public int getPoolSize() {
		return poolSize;
	}

	public int setPoolSize() {
		this.poolSize = poolSize + 1;
		return poolSize;
	}

	public synchronized int setPoolSizeAync() {
		this.poolSize = poolSize + 1;
		return poolSize;
	}

	private static class MyTask implements Runnable {

		private ThreadPoolExecutorAnaly threadPoolExecutorAnaly;

		public MyTask(ThreadPoolExecutorAnaly threadPoolExecutorAnaly) {
			this.setThreadPoolExecutorAnaly(threadPoolExecutorAnaly);
		}

		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + ">>>MyTask2>>" + threadPoolExecutorAnaly.setPoolSize());
			System.out.println("end.......................................");
		}

		public void setThreadPoolExecutorAnaly(ThreadPoolExecutorAnaly threadPoolExecutorAnaly) {
			this.threadPoolExecutorAnaly = threadPoolExecutorAnaly;
		}
	}

	private static class MyTaskSync implements Runnable {

		private ThreadPoolExecutorAnaly threadPoolExecutorAnaly;

		public MyTaskSync(ThreadPoolExecutorAnaly threadPoolExecutorAnaly) {
			this.setThreadPoolExecutorAnaly(threadPoolExecutorAnaly);
		}

		@Override
		public void run() {
			System.out.println(
					Thread.currentThread().getName() + ">>>MyTaskSync2>>" + threadPoolExecutorAnaly.setPoolSizeAync());
			System.out.println("end.........");
		}

		public void setThreadPoolExecutorAnaly(ThreadPoolExecutorAnaly threadPoolExecutorAnaly) {
			this.threadPoolExecutorAnaly = threadPoolExecutorAnaly;
		}
	}
}
