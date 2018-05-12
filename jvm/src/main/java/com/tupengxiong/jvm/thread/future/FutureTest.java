/** 
 * Project Name:jvm 
 * File Name:future.java 
 * Package Name:com.tupengxiong.jvm.thread 
 * Date:2018年5月5日下午5:56:46 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.thread.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ClassName:future <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年5月5日 下午5:56:46 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class FutureTest {
	public static void main(String[] args) {
		Callable<Integer> callable = new Callable<Integer>() {
			public Integer call() throws Exception {
				Thread.sleep(3000);
				return new Random().nextInt(100);
			}
		};
		
		/**
		 * FutureTask implements  RunnableFuture
		 * 		CAS 机制
		 * 
		 * 		RunnableFuture extends Runnable, Future
		 * 
		 * 			Future 
		 * 				boolean cancel(boolean mayInterruptIfRunning);
		 * 				boolean isCancelled();
		 * 				boolean isDone();
    	 * 				V get() throws InterruptedException, ExecutionException;
   		 * 				V get(long timeout, TimeUnit unit)
   		 * 		Runnable
   		 * 				void run()
		 */
		FutureTask<Integer> future = new FutureTask<Integer>(callable);
		new Thread(future).start();
		try {
			Thread.sleep(1000);// 可能做一些事情
			System.out.println(future.get(2L,TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			
			e.printStackTrace();
		}
	}
}
