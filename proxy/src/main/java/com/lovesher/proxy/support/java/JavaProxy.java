/** 
 * Project Name:proxy 
 * File Name:CGProxy.java 
 * Package Name:proxy.support 
 * Date:2018年5月2日下午4:57:56 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.proxy.support.java;

import java.lang.reflect.Proxy;

/**
 * ClassName:JAVAProxy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年5月2日 下午4:57:56 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class JavaProxy {

	public static Object getProxy(Class<?>[] interfaces, InvocationHandler invocationHandler) {
		return Proxy.newProxyInstance(JavaProxy.class.getClassLoader(), interfaces, invocationHandler);
	}
}
