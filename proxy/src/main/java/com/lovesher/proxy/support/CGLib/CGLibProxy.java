/** 
 * Project Name:proxy 
 * File Name:CGProxy.java 
 * Package Name:proxy.support 
 * Date:2018年5月2日下午4:57:56 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.proxy.support.CGLib;

import net.sf.cglib.proxy.Enhancer;

/**
 * ClassName:CGProxy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年5月2日 下午4:57:56 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class CGLibProxy{
	
	public static Object getProxy(Class<?>[] interfaces,MethodInterceptor interceptor) {
		Enhancer enhancer = new Enhancer(); // 创建加强器，用来创建动态代理类
		enhancer.setInterfaces(interfaces); // 为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
		// 设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
		enhancer.setCallback(interceptor);
		// 创建动态代理类对象并返回
		return enhancer.create();
	}
}
