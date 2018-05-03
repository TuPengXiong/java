/** 
 * Project Name:proxy 
 * File Name:ProxyFactory.java 
 * Package Name:proxy 
 * Date:2018年5月2日下午4:55:19 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.proxy;

import com.lovesher.proxy.support.CGLib.CGLibProxy;
import com.lovesher.proxy.support.CGLib.MethodInterceptor;
import com.lovesher.proxy.support.java.InvocationHandler;
import com.lovesher.proxy.support.java.JavaProxy;

/** 
 * ClassName:ProxyFactory <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年5月2日 下午4:55:19 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class ProxyFactory {

	public static Object getCGLibProxy(Class<?>[] interfaces,MethodInterceptor interceptor){
		return CGLibProxy.getProxy(interfaces, interceptor);
	}
	
	public static Object getJavaProxy(Class<?>[] interfaces,InvocationHandler invocationHandler){
		return JavaProxy.getProxy(interfaces, invocationHandler);
	}
}
  