package com.lovesher.excel;

import java.lang.reflect.Method;

/**
 * 反射帮助类
 */
public class ReflectUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object invokeGetMethod(Class claszz,Object o,String name){
		Object ret=null;
		try {
			Method method=claszz.getMethod("get"+StringUtils.firstCharUpperCase(name));
			ret=method.invoke(o);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invokeSetMethod(Class claszz,Object o,String name,Class[] argTypes,Object[] args){
		Object ret=null;
		try {
			Method method=claszz.getMethod("set"+StringUtils.firstCharUpperCase(name),argTypes);
			ret=method.invoke(o,args);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invokeSetMethod(Class claszz,Object o,String name,Class argType,Object args){
		Object ret=null;
		try {
			Method method=claszz.getMethod("set"+StringUtils.firstCharUpperCase(name),new Class[]{argType});
			ret=method.invoke(o,new Object[]{argType});
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return ret;
	}
	
}