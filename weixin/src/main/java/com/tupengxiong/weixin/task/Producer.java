/** 
 * Project Name:jvm 
 * File Name:Producer.java 
 * Package Name:com.tupengxiong.jvm.pc 
 * Date:2017年11月3日下午2:23:51 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.weixin.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * ClassName:Producer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月3日 下午2:23:51 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public abstract class Producer<E> implements Runnable,InitializingBean {

	private static final Logger logger = Logger.getLogger(Producer.class);
	private final int producerNum;

	private final int consumerNum;

	// 生产阻塞队列
	private LinkedBlockingQueue<E> QUEUE = null;

	// 非阻塞队列
	// public ConcurrentLinkedQueue<E> QUEUE = new ConcurrentLinkedQueue<E>();

	// 消费者线程池
	private ExecutorService consumerExecutorService = null;

	// 消费者线程数量
	private AtomicInteger atomicInteger = null;

	public Producer(int producerNum, int consumerNum) {
		this.consumerNum = consumerNum;
		this.producerNum = producerNum;
		QUEUE = new LinkedBlockingQueue<E>(producerNum);
		consumerExecutorService = Executors.newScheduledThreadPool(consumerNum);
		atomicInteger = new AtomicInteger(consumerNum);
	}

	/*
	 * public Producer() { this.consumerNum = 1; this.producerNum = 1; QUEUE =
	 * new LinkedBlockingQueue<E>(producerNum); consumerExecutorService =
	 * Executors.newScheduledThreadPool(consumerNum); atomicInteger = new
	 * AtomicInteger(consumerNum); }
	 */

	public abstract void produce();

	public abstract Consumer<E> getConsumer(E e);

	@Override
	public void run() {
		while (true) {
			if (QUEUE.isEmpty() && atomicInteger.get()>0) {
				produce();
				
				if (QUEUE.isEmpty()) {
					synchronized (this) {
						try {
							wait(5000);
							logger.info("wait(5000)>>>>生产已经消费完毕,等待生产>>>" + QUEUE.size());
						} catch (InterruptedException e) {

							e.printStackTrace();
						}
					}
				}
			}
			int size = atomicInteger.get() > QUEUE.size() ? QUEUE.size() : atomicInteger.get();
			for (int i = 0; i < size; i++) {
				atomicInteger.decrementAndGet();
				consumerExecutorService.execute(getConsumer(QUEUE.poll()));
			}

			if (atomicInteger.get() <= 0) {
				synchronized (this) {
					try {
						logger.info("wait(1000)>>>>消费线程使用完毕,等待线程任务完成>>>" + atomicInteger.get());
						wait(1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}

		}

	}

	public LinkedBlockingQueue<E> getQUEUE() {
		return QUEUE;
	}

	public void setQUEUE(LinkedBlockingQueue<E> qUEUE) {
		QUEUE = qUEUE;
	}

	public ExecutorService getConsumerExecutorService() {
		return consumerExecutorService;
	}

	public void setConsumerExecutorService(ExecutorService consumerExecutorService) {
		this.consumerExecutorService = consumerExecutorService;
	}

	public AtomicInteger getAtomicInteger() {
		return atomicInteger;
	}

	public void setAtomicInteger(AtomicInteger atomicInteger) {
		this.atomicInteger = atomicInteger;
	}

	public int getProducerNum() {
		return producerNum;
	}

	public int getConsumerNum() {
		return consumerNum;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		new Thread(this).start();
	}
}
