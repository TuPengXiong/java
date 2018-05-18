/** 
 * Project Name:jvm 
 * File Name:ObjectFactory.java 
 * Package Name:com.tupengxiong.jvm.beans 
 * Date:2018年4月12日上午10:38:19 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.jvm.beans;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.tupengxiong.jvm.reflect.ReflectUtils;

/**
 * ClassName:ObjectFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月12日 上午10:38:19 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ObjectFactory {

	/**
	 * 默认的构造器实例化 getInstance:默认的构造器实例化. <br/>
	 * 
	 * @author tupengxiong
	 * @param claz
	 * @return
	 * @throws RuntimeException
	 * @since JDK 1.7
	 */
	public static Object getInstance(Class<?> claz) throws RuntimeException {
		return ReflectUtils.getInstance().getInstance(claz);
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
	public static Object getInstance(Class<?> claz, Class<?>[] parameterTypes, Object[] parameterValues)
			throws RuntimeException {
		return ReflectUtils.getInstance().getInstance(claz, parameterTypes, parameterValues);
	}

	public static void main(String[] args) {

		//new
		User u1 = (User) printNewInstance(User.class);

		User u2 = (User) printNewInstance(User.class, new Class<?>[] { Long.class, String.class, String.class, Date.class, Date.class },
				new Object[] { 1L, "1", "1", new Date(), new Date() });

		User u3 = (User) printNewInstance(User.class, new Class<?>[] { Long.class }, new Object[] { 1L });

		//UserInterface userInterface = (UserInterface) printNewInstance(UserInterface.class);

		//UserAbstract userAbstract = (UserAbstract) printNewInstance(UserAbstract.class);

		//set
		ReflectUtils.getInstance().invokeSetMethod(u2, "id", 4L);
		
		printObject(u2);
		//get 
		printObject(ReflectUtils.getInstance().invokeGetMethod(u2, "id"));
		
		/**
		 * {
		 * 
		 * "id":{"accessible":false,"annotations":[],"declaringClass":"com.tupengxiong.jvm.beans.User","enumConstant":false,"genericType":"java.lang.Long","modifiers":2,"name":"id","synthetic":false,"type":"java.lang.Long"},
		 * "createTime":{"accessible":false,"annotations":[],"declaringClass":"com.tupengxiong.jvm.beans.User","enumConstant":false,"genericType":"java.util.Date","modifiers":2,"name":"createTime","synthetic":false,"type":"java.util.Date"},
		 * "username":{"accessible":false,"annotations":[],"declaringClass":"com.tupengxiong.jvm.beans.User","enumConstant":false,"genericType":"java.lang.String","modifiers":2,"name":"username","synthetic":false,"type":"java.lang.String"},
		 * "updateTime":{"accessible":false,"annotations":[],"declaringClass":"com.tupengxiong.jvm.beans.User","enumConstant":false,"genericType":"java.util.Date","modifiers":2,"name":"updateTime","synthetic":false,"type":"java.util.Date"},
		 * "password":{"accessible":false,"annotations":[],"declaringClass":"com.tupengxiong.jvm.beans.User","enumConstant":false,"genericType":"java.lang.String","modifiers":2,"name":"password","synthetic":false,"type":"java.lang.String"}
		 * }
		 */
		printObject(ReflectUtils.getInstance().getFileds(u1.getClass()));
		
		printObject(ReflectUtils.getInstance().getFileds(u3.getClass()));
		
		printObject(ReflectUtils.getInstance().getFileds(UserInterface.class));
		
		printObject(ReflectUtils.getInstance().getFileds(UserAbstract.class));
	}

	private static Object printNewInstance(Class<?> claz) {
		Object object = null;
		try {
			object = ObjectFactory.getInstance(claz);
			printObject(object);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(claz.getCanonicalName());
		}
		return object;
	}

	private static Object printNewInstance(Class<?> claz, Class<?>[] parameterTypes, Object[] parameterValues) {
		Object object = null;
		try {
			 object = ObjectFactory.getInstance(claz, parameterTypes, parameterValues);
			 printObject(object);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(claz.getCanonicalName());
		}
		return object;
	}
	
	private static void printObject(Object object){
		try {
			System.out.println(JSON.toJSONString(object));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(object.getClass().getCanonicalName());
		}
	}
}
