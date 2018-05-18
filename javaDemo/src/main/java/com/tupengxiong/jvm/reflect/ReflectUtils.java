/** 
 * Project Name:jvm 
 * File Name:ReflectUtils.java 
 * Package Name:com.tupengxiong.jvm.reflect 
 * Date:2018年4月12日上午10:33:19 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * ClassName:ReflectUtils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月12日 上午10:33:19 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ReflectUtils {

	private final static String COMMON_SET = "set";
	private final static String BOOLEN_GET = "is";
	private final static String COMMON_GET = "get";

	private static ReflectUtils reflectUtils = null;

	private ReflectUtils() {

	}
	public synchronized static ReflectUtils getInstance() {
		synchronized (ReflectUtils.class) {
			if (null == reflectUtils) {
				reflectUtils = new ReflectUtils();
			}
		}
		return reflectUtils;
	}

	/**
	 * 默认的构造器实例化 getInstance:默认的构造器实例化. <br/>
	 * 
	 * @author tupengxiong
	 * @param claz
	 * @return
	 * @throws RuntimeException
	 * @since JDK 1.7
	 */
	public Object getInstance(Class<?> claz) throws RuntimeException {
		if (claz == null) {
			throw new NoClassDefFoundError("");
		}
		try {
			return claz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 通过构造器来实例化对象 getInstance:通过构造器来实例化对象. <br/>
	 * 
	 * @author tupengxiong
	 * @param claz
	 * @param parameterTypes
	 * @param parameterValues
	 * @return
	 * @throws RuntimeException
	 * @since JDK 1.7
	 */
	public Object getInstance(Class<?> claz, Class<?>[] parameterTypes, Object[] parameterValues)
			throws RuntimeException {
		if (claz == null) {
			throw new NullPointerException(" claz is null");
		}
		try {
			Constructor<?> constructor = claz.getDeclaredConstructor(parameterTypes);
			return constructor.newInstance(parameterValues);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void invokeSetMethod(Object obj, String field, Object value) {
		
		Class<?> claz = obj.getClass();
		Field clazField = getFiled(claz, field);
		if (null == clazField) {
			throw new RuntimeException("no such field:[" + field + "]");
		}
		Method method = null;
		try {
			method = claz.getMethod(getFiledSetMethod(field),clazField.getType());
			method.invoke(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
			// ignore
			method = null;
		}
		if (null == method) {
			new RuntimeException("invokeSetMethod error:[" + field + "]");
		}
	}

	public Object invokeGetMethod(Object obj, String field) {
		Class<?> claz = obj.getClass();
		Field clazField = getFiled(claz, field);
		if (null == clazField) {
			return null;
		}
		Method method = null;
		Class<?> fieldType = clazField.getType();
		try {
			if (fieldType == boolean.class || fieldType == Boolean.class) {
				method = claz.getMethod(getFiledGetMethod(field, BOOLEN_GET));
			}
			if(method == null){
				method = claz.getMethod(getFiledGetMethod(field, COMMON_GET));
			}
			return method.invoke(obj);
		} catch (Exception e) {
			// ignore
			method = null;
		}
		return null;
	}

	public String getFiledSetMethod(String field) {
		return new StringBuilder(COMMON_SET).append(field.substring(0, 1).toUpperCase()).append(field.substring(1))
				.toString();
	}

	public String getFiledGetMethod(String field, String getPrefix) {
		return new StringBuilder(getPrefix).append(field.substring(0, 1).toUpperCase()).append(field.substring(1))
				.toString();
	}

	/**
	 * 获取所有的属性 getFileds:获取所有的属性. <br/>
	 * 
	 * @author tupengxiong
	 * @param claz
	 * @return
	 * @since JDK 1.7
	 */
	public Map<String,Field> getFileds(Class<?> claz) {
		Map<String,Field> fields = new HashMap<String,Field>();
		Class<?> currClaz = claz;
		while (currClaz != null) {
			// getDeclaredFields()获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
			// getFields()获得某个类的所有的公共（public）的字段，包括父类。
			for(int i=0;i<currClaz.getDeclaredFields().length;i++){
				fields.put(currClaz.getDeclaredFields()[i].getName(),currClaz.getDeclaredFields()[i]);
			}
			currClaz = currClaz.getSuperclass();
		}
		return fields;
	}

	/**
	 * 获取所有的属性 getFileds:获取所有的属性. <br/>
	 * 
	 * @author tupengxiong
	 * @param claz
	 * @return
	 * @since JDK 1.7
	 */
	public Field getFiled(Class<?> claz, String field) {
		return getFileds(claz).get(field);
	}
}
