package com.tupengxiong.weixin.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {

	private static final Logger logger = Logger.getLogger(BeanUtils.class);

	public List<String> beanFieldNames(Class<?> className) {
		Field[] fields = className.getDeclaredFields();
		Field[] superFields = className.getSuperclass().getDeclaredFields();
		Field.setAccessible(fields, true);
		Field.setAccessible(superFields, true);
		List<String> fieldNames = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			fieldNames.add(fields[i].getName());
			logger.info(fields[i].getName());
		}
		logger.info(superFields.length);
		logger.info(className.getSuperclass().getName());
		for (int i = 0; i < superFields.length; i++) {
			fieldNames.add(superFields[i].getName());
			logger.info(superFields[i].getName());
		}
		return fieldNames;
	}

	public void fillfieldValueByNotRootElement(Element element, Object obj) {
		if (null == element) {
			return;
		}
		String fieldName = indexChractarToLowerCase(element.getName());
		Class<?> className = obj.getClass();
		Class<?> classType = null;
		Field field = null;
		Method method = null;
		try {
			try {
				field = obj.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				classType = field.getType();
				method = obj.getClass().getDeclaredMethod("set" + element.getName(), classType);
				method.setAccessible(true);
			} catch (NoSuchFieldException e) {
				logger.error(new StringBuilder("NoSuchFieldException className=").append(className.getName())
						.append(" Field = ").append(fieldName));
			}
			if (null == field) {
				try {
					className = obj.getClass().getSuperclass();
					field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
					classType = field.getType();
					method = obj.getClass().getSuperclass().getDeclaredMethod("set" + element.getName(), classType);
					method.setAccessible(true);
				} catch (NoSuchFieldException e) {
					logger.error(new StringBuilder("NoSuchFieldException className=").append(className.getName())
							.append(" Field = ").append(fieldName));
				}

			}
			convert(classType, method, element.getText(), obj);
		} catch (NoSuchMethodException e) {
			logger.error(new StringBuilder("NoSuchMethodException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		} catch (IllegalAccessException e) {
			logger.error(new StringBuilder("IllegalAccessException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		} catch (IllegalArgumentException e) {
			logger.error(new StringBuilder("IllegalArgumentException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		} catch (InvocationTargetException e) {
			logger.error(new StringBuilder("InvocationTargetException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		} catch (ParseException e) {
			logger.error(new StringBuilder("ParseException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		} catch (SecurityException e) {
			logger.error(new StringBuilder("SecurityException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		}

	}

	public String indexChractarToUpperCase(String fldName) {
		String first = fldName.substring(0, 1).toUpperCase();
		String rest = fldName.substring(1, fldName.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}

	public String indexChractarToLowerCase(String fldName) {
		String first = fldName.substring(0, 1).toLowerCase();
		String rest = fldName.substring(1, fldName.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}

	public void convert(Class<?> xclass, Method setMethod, String value, Object tObject)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		if (xclass.toString().equals("class java.lang.String")) {
			setMethod.invoke(tObject, value);
		} else if (xclass.toString().equals("class java.util.Date")) {
			setMethod.invoke(tObject, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value));
		} else if (xclass.toString().equals("class java.lang.Boolean")) {
			Boolean boolname = true;
			if (value.equals("否") || value.equals("false")) {
				boolname = false;
			}
			setMethod.invoke(tObject, boolname);
		} else if (xclass.toString().equals("class java.lang.Integer")) {
			setMethod.invoke(tObject, new Integer(value));
		} else if (xclass.toString().equals("class java.lang.Long")) {
			setMethod.invoke(tObject, new Long(value));
		}
	}
}
