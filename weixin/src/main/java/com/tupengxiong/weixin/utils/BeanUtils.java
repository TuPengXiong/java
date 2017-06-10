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
		Field.setAccessible(fields, true);
		List<String> fieldNames = new ArrayList<String>();
		for (int i = 0; i < fields.length; i++) {
			fieldNames.add(fields[i].getName());
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
		try {
			field = obj.getClass().getDeclaredField(fieldName);
			if (null == field) {
				return;
			}
			classType = field.getType();
			Method method = className.getDeclaredMethod("set" + element.getName(), classType);
			convert(classType, method, element.getText(), obj);
		} catch (NoSuchMethodException | SecurityException e) {
			logger.error(new StringBuilder("NoSuchMethodException className=").append(className.getName())
					.append(" Method =set").append(element.getName()));
		} catch (NoSuchFieldException e) {
			logger.error(new StringBuilder("NoSuchFieldException className=").append(className.getName())
					.append(" Field = ").append(fieldName));
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
