/** 
 * Project Name:jvm 
 * File Name:ThreadNotify.java 
 * Package Name:com.tupengxiong.jvm.thread 
 * Date:2018年2月2日上午10:42:16 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName:ThreadNotify <br/>
 * Function: 使用 wait notify 实现一个队列，队列有2个方法，add 和 get 。
 * add方法往队列中添加元素，get方法往队列中获得元素。队列必须是线程安全的。 如果get执行时，队列为空，线程必须阻塞等待，直到有队列有数据。
 * 如果add时，队列已经满，则add线程要等待，直到队列有空闲空间。
 * 实现这么一个队列，并写一个测试代码，使他工作在多线程的环境下，证明，它的工作是正确的。给出程序和运行的截图。. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年2月2日 上午10:42:16 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ThreadQueue<T> implements Runnable {

	private Queue<T> queue;

	private EnumType type;
	
	private T content;

	enum EnumType {
		add, get;
	}

	public ThreadQueue(EnumType type, Queue<T> queue,T content) {
		this.type = type;
		this.queue = queue;
		this.content = content;
	}

	public ThreadQueue() {

	}

	/**
	 * ----------------------------------------------------
		add>>>>>>>>pool-1-thread-1>>>>>>>>结束,内容为:addContent1
		add>>>>>>>>pool-1-thread-2>>>>>>>>结束,内容为:addContent2
		add>>>>>>>>pool-1-thread-3>>>>>>>>开始阻塞等待
		----------------------------------------------------
		get>>>>>>>>pool-1-thread-2>>>>>>>>结束,内容为:addContent1
		addContent1
		add>>>>>>>>pool-1-thread-3>>>>>>>>阻塞等待时间为：9989
		add>>>>>>>>pool-1-thread-3>>>>>>>>结束,内容为:addContent3
		get>>>>>>>>pool-1-thread-3>>>>>>>>结束,内容为:addContent2
		addContent2
		get>>>>>>>>pool-1-thread-3>>>>>>>>结束,内容为:addContent3
		addContent3
		get>>>>>>>>pool-1-thread-3>>>>>>>>开始阻塞等待
		----------------------------------------------------
		add>>>>>>>>pool-1-thread-2>>>>>>>>结束,内容为:addContent4
		get>>>>>>>>pool-1-thread-3>>>>>>>>阻塞等待时间为：10000
		get>>>>>>>>pool-1-thread-3>>>>>>>>结束,内容为:addContent4
		addContent4
		get>>>>>>>>pool-1-thread-3>>>>>>>>开始阻塞等待
		----------------------------------------------------
		add>>>>>>>>pool-1-thread-2>>>>>>>>结束,内容为:addContent5
		get>>>>>>>>pool-1-thread-3>>>>>>>>阻塞等待时间为：10000
		get>>>>>>>>pool-1-thread-3>>>>>>>>结束,内容为:addContent5
		addContent5
	 * @author tupengxiong
	 * @param args
	 * @throws InterruptedException 
	 * @since JDK 1.7
	 */
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();

		Queue<String> queue = new Queue<String>(2);

		ThreadQueue<String> addQueue1 = new ThreadQueue<String>(EnumType.add, queue,"addContent1");
		ThreadQueue<String> addQueue2 = new ThreadQueue<String>(EnumType.add, queue,"addContent2");
		ThreadQueue<String> addQueue3 = new ThreadQueue<String>(EnumType.add, queue,"addContent3");
		ThreadQueue<String> addQueue4 = new ThreadQueue<String>(EnumType.add, queue,"addContent4");
		ThreadQueue<String> addQueue5 = new ThreadQueue<String>(EnumType.add, queue,"addContent5");
		
		ThreadQueue<String> getQueue1 = new ThreadQueue<String>(EnumType.get, queue,"getContent1");
		ThreadQueue<String> getQueue2 = new ThreadQueue<String>(EnumType.get, queue,"getContent2");
		ThreadQueue<String> getQueue3 = new ThreadQueue<String>(EnumType.get, queue,"getContent3");
		ThreadQueue<String> getQueue4 = new ThreadQueue<String>(EnumType.get, queue,"getContent4");
		ThreadQueue<String> getQueue5 = new ThreadQueue<String>(EnumType.get, queue,"getContent5");

		System.out.println("----------------------------------------------------");
		//添加三条 第三天处于阻塞状态
		executorService.execute(addQueue1);
		executorService.execute(addQueue2);
		executorService.execute(addQueue3);
		

		//获取一条之后  添加的第三条执行 获取的第四条处于阻塞状态   
		Thread.sleep(10000L);
		System.out.println("----------------------------------------------------");
		executorService.execute(getQueue1);
		executorService.execute(getQueue2);
		executorService.execute(getQueue3);
		executorService.execute(getQueue4);
		
		Thread.sleep(10000L);
		System.out.println("----------------------------------------------------");
		executorService.execute(addQueue4);
		executorService.execute(getQueue5);
		
		
		Thread.sleep(10000L);
		System.out.println("----------------------------------------------------");
		executorService.execute(addQueue5);
		
		executorService.shutdown();
	}

	@Override
	public void run() {
		if (type == EnumType.add) {
			try {
				queue.add(content);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			try {
				System.out.println(queue.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public EnumType getType() {
		return type;
	}

	public void setType(EnumType type) {
		this.type = type;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
