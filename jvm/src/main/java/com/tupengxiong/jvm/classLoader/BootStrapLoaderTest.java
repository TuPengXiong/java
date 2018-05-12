/** 
 * Project Name:jvm 
 * File Name:BootStrapLoaderTest.java 
 * Package Name:com.tupengxiong.jvm.classLoader 
 * Date:2018年4月9日下午7:45:55 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.classLoader;  
/** 
 * ClassName:BootStrapLoaderTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年4月9日 下午7:45:55 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class BootStrapLoaderTest {

	public static void main(String[] args) {
		ClassLoader classLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String string) throws ClassNotFoundException {
				
				return super.loadClass(string);
			}
		};

	}
}
  