package com.tupengxiong.reflect;

import java.lang.annotation.Annotation;
import java.util.Map;

public class JavaFiledDescription {

	
	private String name;
	
	private Class<?> beanClaz;
	
	private Class<?> type;
	
	private Map<Class<? extends Annotation>,Annotation> annotations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Class<?> getBeanClaz() {
		return beanClaz;
	}

	public void setBeanClaz(Class<?> beanClaz) {
		this.beanClaz = beanClaz;
	}

	public Map<Class<? extends Annotation>, Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<Class<? extends Annotation>, Annotation> annotations) {
		this.annotations = annotations;
	}
	
}
