/** 
 * Project Name:jvm 
 * File Name:ThreadFIFO.java 
 * Package Name:com.tupengxiong.jvm.thread 
 * Date:2018年2月2日上午9:49:39 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.thread;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName:ThreadFIFO <br/>
 * Function: 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行. <br/>
 * Reason: 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行. <br/>
 * Date: 2018年2月2日 上午9:49:39 <br/>
 * CountDownLatch CAS 原理 compareAndSet
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ThreadFIFO {

	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		BaseThread t1 = new BaseThread("t1", 1L, countDownLatch);
		t1.start();
		countDownLatch.await();
		countDownLatch = new CountDownLatch(1);
		BaseThread t2 = new BaseThread("t2", 3L, countDownLatch);
		t2.start();
		countDownLatch.await();

		countDownLatch = new CountDownLatch(1);
		BaseThread t3 = new BaseThread("t3", 2L, countDownLatch);
		t3.start();
	}

	
	private static class BaseThread extends Thread {

		private String nameThread;
		private Long sleepTimeSec;
		private CountDownLatch countDownLatch;

		public BaseThread(String nameThread, Long sleepTimeSec, CountDownLatch countDownLatch) {
			this.nameThread = nameThread;
			this.sleepTimeSec = sleepTimeSec;
			this.countDownLatch = countDownLatch;
		}

		public BaseThread(String nameThread) {
			this.nameThread = nameThread;
		}

		public BaseThread(String nameThread, Long sleepTimeSec) {
			this.nameThread = nameThread;
			this.sleepTimeSec = sleepTimeSec;
		}

		@Override
		public void run() {
			try {
				if (null != sleepTimeSec && sleepTimeSec > 0) {
					Thread.sleep(sleepTimeSec * 1000L);
				}
				System.out.println("thread name--------------" + nameThread + "-----------------");
			} catch (InterruptedException e) {

				e.printStackTrace();
			} finally {
				if (countDownLatch != null) {
					countDownLatch.countDown();
				}
			}
		}

		public String getNameThread() {
			return nameThread;
		}

		public void setNameThread(String nameThread) {
			this.nameThread = nameThread;
		}

		public Long getSleepTimeSec() {
			return sleepTimeSec;
		}

		public void setSleepTimeSec(Long sleepTimeSec) {
			this.sleepTimeSec = sleepTimeSec;
		}

	}
}
