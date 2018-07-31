package com.tupengxiong.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ReflectionUtils
 * @ClassName: ReflectionUtils 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author tupx 
 * @date 2018年7月31日 上午9:37:26 
 *
 */
public class ReflectionUtils {

	
	public static List<JavaMethodDescription> getMethodDescriptions(Class<?> claz){
		Class<?> currClaz = claz;
		List<JavaMethodDescription> descriptions = new ArrayList<JavaMethodDescription>();
		while(currClaz != Object.class){
			for(Method method:claz.getDeclaredMethods()){
				JavaMethodDescription javaMethodDescription = new JavaMethodDescription();
				javaMethodDescription.setBeanClaz(claz);
				javaMethodDescription.setName(method.getName());
				javaMethodDescription.setReturnClaz(method.getReturnType());
				javaMethodDescription.setParams(method.getParameterTypes());
				javaMethodDescription.setAnnotations(getAnnotations(method.getDeclaredAnnotations()));
				descriptions.add(javaMethodDescription);
			}
			currClaz = currClaz.getSuperclass();
		}
		return descriptions;
	}
	
	
	public static List<JavaFiledDescription> getFieldDescriptions(Class<?> claz){
		Class<?> currClaz = claz;
		List<JavaFiledDescription> descriptions = new ArrayList<JavaFiledDescription>();
		while(currClaz != Object.class){
			for(Field field:claz.getDeclaredFields()){
				JavaFiledDescription javaFiledDescription = new JavaFiledDescription();
				javaFiledDescription.setBeanClaz(claz);
				javaFiledDescription.setName(field.getName());
				javaFiledDescription.setType(field.getType());
				javaFiledDescription.setAnnotations(getAnnotations(field.getDeclaredAnnotations()));
				descriptions.add(javaFiledDescription);
			}
			currClaz = currClaz.getSuperclass();
		}
		return descriptions;
	}
	
	public static List<JavaConstructorDescription> getConstructorDescriptions(Class<?> claz){
		Class<?> currClaz = claz;
		List<JavaConstructorDescription> descriptions = new ArrayList<JavaConstructorDescription>();
		while(currClaz != Object.class){
			for(Constructor<?> constructor:claz.getConstructors()){
				JavaConstructorDescription javaConstructorDescription = new JavaConstructorDescription();
				javaConstructorDescription.setBeanClaz(claz);
				javaConstructorDescription.setName(constructor.getName());
				javaConstructorDescription.setParams(constructor.getParameterTypes());
				javaConstructorDescription.setAnnotations(getAnnotations(constructor.getDeclaredAnnotations()));
				descriptions.add(javaConstructorDescription);
			}
			currClaz = currClaz.getSuperclass();
		}
		return descriptions;
	}
	
	public static Map<Class<? extends Annotation>,Annotation> getAnnotations(Annotation[] annotations){
		 Map<Class<? extends Annotation>,Annotation>  map = new HashMap<Class<? extends Annotation>,Annotation>();
		for(Annotation annotation:annotations){
			map.put(annotation.getClass(), annotation);
		}
		return map;
	}
}
