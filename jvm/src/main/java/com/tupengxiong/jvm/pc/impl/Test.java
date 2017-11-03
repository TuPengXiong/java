/** 
 * Project Name:jvm 
 * File Name:Test.java 
 * Package Name:com.tupengxiong.jvm.pc.impl 
 * Date:2017年11月3日下午3:52:50 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.pc.impl;  
/** 
 * ClassName:Test <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年11月3日 下午3:52:50 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class Test {

	public static void main(String[] args) {
		ProducerUser producerUser = new ProducerUser(1000,2000);
		new Thread(producerUser).start();
	}
}
  