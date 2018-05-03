/** 
 * Project Name:proxy 
 * File Name:MethodInterceptor.java 
 * Package Name:com.lovesher.proxy.support.CG 
 * Date:2018年5月3日上午11:53:23 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.proxy.support.CGLib;

import java.lang.reflect.Method;

import com.lovesher.proxy.support.Extend;

import net.sf.cglib.proxy.MethodProxy;

/** 
 * ClassName:MethodInterceptor <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年5月3日 上午11:53:23 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class MethodInterceptor implements net.sf.cglib.proxy.MethodInterceptor{

	private Object target;

	private Extend extend;

	public MethodInterceptor(Object target) {
		super();
		this.setTarget(target);
	}

	public MethodInterceptor(Object target, Extend extend) {
		super();
		this.setTarget(target);
		this.setExtend(extend);
	}

	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
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

	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}

}
  