package com.tupengxiong.jvm.thread.voliate;

public class MyThread implements Runnable {
	private volatile Integer num = 0;
	private Integer num1 = 0;

	@Override
	public void run() {
		num += 1;
		System.out.println("num =" + num);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		num1 += 1;
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("num1 =" + num1);
	}
}