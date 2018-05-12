/** 
 * Project Name:jvm 
 * File Name:VoliateTest.java 
 * Package Name:com.tupengxiong.jvm.thread.voliate 
 * Date:2018年3月30日下午2:22:44 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.thread.voliate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** 
 * ClassName:VoliateTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月30日 下午2:22:44 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class VoliateTest {

	
	private static ExecutorService fixedExecutorService = Executors.newFixedThreadPool(3);
	
	/**
	 * 	num =1
		num =2
		num =3
		num1 =3
		num =4
		num1 =3
		num =5
		num1 =3
		num =6
		num1 =6
		num =7
		num1 =6
		num1 =6
	 * @author tupengxiong
	 * @param args 
	 * @since JDK 1.7
	 */
	public static void main(String[] args) {
		MyThread thread = new MyThread();
		fixedExecutorService.execute(thread);
		fixedExecutorService.execute(thread);
		fixedExecutorService.execute(thread);
		fixedExecutorService.execute(thread);
		fixedExecutorService.execute(thread);
		fixedExecutorService.execute(thread);
		fixedExecutorService.execute(thread);
	}
}
  