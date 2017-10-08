package com.tupengxiong.elasticsearch.bean.base;

import com.tupengxiong.elasticsearch.annotation.EsSearch;

/**
 * 反射机制获取class的属性名字、属性值、属性包含EsSearch的注解、属性的类型
 * @author tupx
 */
public class BaseAnnotationResult {

	private String propertyName;
	
	private Object propertyValue;
	
	private EsSearch esSearch;
	
	private Class<?> propertyClass;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public EsSearch getEsSearch() {
		return esSearch;
	}

	public void setEsSearch(EsSearch esSearch) {
		this.esSearch = esSearch;
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	public void setPropertyClass(Class<?> propertyClass) {
		this.propertyClass = propertyClass;
	}
	
	
}
