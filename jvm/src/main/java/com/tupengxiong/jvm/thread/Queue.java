package com.tupengxiong.jvm.thread;

import java.util.ArrayList;
import java.util.List;

public class Queue<T> {

	private int queueSize;

	private List<T> lists = new ArrayList<T>();

	public Queue(int queueSize) {
		this.queueSize = queueSize;
	}

	public void add(T t) throws InterruptedException {
		synchronized (this) {
			while (lists.size() >= queueSize) {
				// 队列已经满了
				System.out.println("add>>>>>>>>" + Thread.currentThread().getName() + ">>>>>>>>开始阻塞等待");
				long startTime = System.currentTimeMillis();
				wait();
				long endTime = System.currentTimeMillis();
				System.out.println("add>>>>>>>>" + Thread.currentThread().getName() + ">>>>>>>>阻塞等待时间为：" + (endTime - startTime));
			}
			lists.add(t);
			System.out.println("add>>>>>>>>" + Thread.currentThread().getName() + ">>>>>>>>结束,内容为:" + t.toString());
			// 通知可以获取get
			notify();
		}
	}

	public T get() throws InterruptedException {
		synchronized (this) {
			
			while (lists.isEmpty()) {
				System.out.println("get>>>>>>>>" + Thread.currentThread().getName() + ">>>>>>>>开始阻塞等待");
				long startTime = System.currentTimeMillis();
				wait();
				long endTime = System.currentTimeMillis();
				System.out.println("get>>>>>>>>" + Thread.currentThread().getName() + ">>>>>>>>阻塞等待时间为：" + (endTime - startTime));
			}
			// 获取第一个元素
			T t = lists.get(0);
			// 删除第一个元素
			lists = lists.subList(1, lists.size());
			
			System.out.println("get>>>>>>>>" + Thread.currentThread().getName() + ">>>>>>>>结束,内容为:" + t.toString());
			notify();
			return t;
		}
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}
}
