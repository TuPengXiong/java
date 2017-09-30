package com.tupengxiong.elasticsearch.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tupengxiong.elasticsearch.annotation.EsSearch;
import com.tupengxiong.elasticsearch.bean.base.BaseAnnotationResult;

public class DataBeanUtil {

	/**
	 * 获取bean中的所有属性以及属性值
	 * 
	 * @Title: getPropertiesMap
	 * @Description: 获取bean中的所有属性以及属性值
	 * @author tupx
	 * @date 2017年9月21日 下午5:16:20
	 * @version V1.0
	 */
	public  static Map<String,BaseAnnotationResult> getPropertiesAndValue(Object bean) {
		Map<String,BaseAnnotationResult> map = new HashMap<String,BaseAnnotationResult>();
		if (bean == null) {
			return map;
		}
		if(bean.getClass() == Object.class){
			return map;
		}
		getProperties(map, bean.getClass());
		for (String field : map.keySet()) {
			Method method;
			try {
				method = bean.getClass().getMethod("get" + firstChartoLowerCase(map.get(field).getPropertyName()));
				Object value = method.invoke(bean);
				map.get(field).setPropertyValue(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static String firstChartoLowerCase(String propertyName){
		return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1, propertyName.length());
	}

	/**
	 * 获取claz中的所有属性名、属性包含EsSearch的信息、属性的类型
	 * 
	 * @Title: getProperties
	 * @Description: 获取claz中的所有属性名、属性包含EsSearch的信息、属性的类型
	 * @author tupx
	 * @date 2017年9月21日 下午5:17:07
	 * @version V1.0
	 */
	public static Map<String,BaseAnnotationResult> getProperties(Map<String,BaseAnnotationResult> map, Class<?> claz) {
		List<Class<?>> clazList = new ArrayList<Class<?>>();
		Class<?> currClaz = claz;
		while(currClaz.getSuperclass() !=Object.class){
			clazList.add(currClaz);
			Field[] fields = claz.getDeclaredFields();
			for (Field field : fields) {
				BaseAnnotationResult annotationResult = new BaseAnnotationResult();
				try {
					annotationResult.setEsSearch(currClaz.getDeclaredField(field.getName()).getAnnotation(EsSearch.class));
					annotationResult.setPropertyName(field.getName());
					annotationResult.setPropertyClass(currClaz.getDeclaredField(field.getName()).getType());
					map.put(field.getName(),annotationResult);
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			}
			currClaz = currClaz.getSuperclass();
		}
		return map;
	}
}
