package com.tupengxiong.reflect;

import java.util.List;

public class JavaBeanDescription {

	private static final JavaBeanDescription JAVA_BEAN_DESCRIPTION = new JavaBeanDescription();
	
	private JavaBeanDescription(){
		
	}

	public static JavaBeanDescription instance(){
		return JAVA_BEAN_DESCRIPTION;
	}
	
	
	public static  List<JavaConstructorDescription> getConstructorDescription(Class<?> claz){
		return ReflectionUtils.getConstructorDescriptions(claz);
	}
	
	public static  List<JavaFiledDescription> getFiledDescription(Class<?> claz){
		return ReflectionUtils.getFieldDescriptions(claz);
	}
	
	public static  List<JavaMethodDescription> getMethodDescription(Class<?> claz){
		return ReflectionUtils.getMethodDescriptions(claz);
	}
}
