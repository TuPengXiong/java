/** 
 * Project Name:proxy 
 * File Name:InvocationHandler.java 
 * Package Name:com.lovesher.proxy.support.java 
 * Date:2018年5月2日下午6:28:57 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.proxy.support.java;

import java.lang.reflect.Method;

import com.lovesher.proxy.support.Extend;

/** 
 * ClassName:InvocationHandler <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年5月2日 下午6:28:57 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class InvocationHandler implements java.lang.reflect.InvocationHandler{

	
	private Object target;
	
	private Extend extend;
	
	public InvocationHandler(Object target) {
		super();
		this.target = target;
	}
	
	public InvocationHandler(Object target, Extend extend) {
		super();
		this.target = target;
		this.extend = extend;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(null != extend){
			extend.doBefore();
		}
		Object object = method.invoke(target, args);
		if(null != extend){
			extend.doAfter();
		}
		return object;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public Object getExtend() {
		return extend;
	}
	public void setExtend(Extend extend) {
		this.extend = extend;
	}

}
  