/** 
 * Project Name:jvm 
 * File Name:BaseThread.java 
 * Package Name:com.tupengxiong.jvm.thread 
 * Date:2018年2月2日上午9:50:12 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.thread;

import java.util.concurrent.CountDownLatch;

/**
 * ClassName:BaseThread <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年2月2日 上午9:50:12 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class BaseThread extends Thread {

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