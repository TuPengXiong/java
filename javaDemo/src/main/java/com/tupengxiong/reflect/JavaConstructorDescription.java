package com.tupengxiong.reflect;

import java.lang.annotation.Annotation;
import java.util.Map;

public class JavaConstructorDescription {

	
	private String name;
	
	private Class<?> beanClaz;
	
	private Class<?>[] params;
	
	private Map<Class<? extends Annotation>,Annotation> annotations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class<?> getBeanClaz() {
		return beanClaz;
	}

	public void setBeanClaz(Class<?> beanClaz) {
		this.beanClaz = beanClaz;
	}

	public Class<?>[] getParams() {
		return params;
	}

	public void setParams(Class<?>[] params) {
		this.params = params;
	}

	public Map<Class<? extends Annotation>, Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Map<Class<? extends Annotation>, Annotation> annotations) {
		this.annotations = annotations;
	}
}
