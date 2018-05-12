/** 
 * Project Name:jvm 
 * File Name:Tes.java 
 * Package Name:com.tupengxiong.jvm.thread.queue 
 * Date:2018年2月2日下午1:51:10 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.thread.queue;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * ClassName:Tes <br/>
 * Function: 比较 ConcurrentLinkedQueue 和
 * BlockingQueue的性能，并说明为什么。给出你的测试代码，和运行结果的截图。. <br/>
 * Reason: 比较 ConcurrentLinkedQueue 和
 * BlockingDeque的性能，并说明为什么。给出你的测试代码，和运行结果的截图。. <br/>
 * Date: 2018年2月2日 下午1:51:10 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class Tes {
	private String name = "111";
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InstantiationException {

		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		Unsafe unsafe = (Unsafe) f.get(null);
		System.out.println(unsafe.addressSize());
		//unsafe.allocateMemory(1024*1024*1024L);
		//创建对象
		//MyThread  myThread = (MyThread) unsafe.allocateInstance(MyThread.class);
		//unsafe.setMemory(myThread, var2, var4, var6);
		
		//myThread.start();
		//unsafe.park(var1, var2);
		
		
	}
	
	class MyThread extends Thread{
		@Override
		public void run() {
			while(true){
				System.out.println("11111");
			}
		}
	}
}
