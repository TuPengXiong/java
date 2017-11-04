package com.tupengxiong.weixin.context;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 手动获取Spring上下文和Bean对象
 *
 * @Author tupengxiong
 * @Date 2017/11/4 17:07
 */
@Service
@Lazy(false)
public class SpringExt implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	private static final Logger logger = Logger.getLogger(SpringExt.class);

	/**
	 * 加载Spring配置文件时，如果Spring配置文件中所定义的Bean类实现了ApplicationContextAware接口，会自动调用该方法
	 *
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringExt.applicationContext = applicationContext;
	}

	/**
	 * 获取Spring上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取Spring Bean
	 * 
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取Spring Bean
	 * 
	 * @param name
	 * @param claz
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class<?> claz) throws BeansException {
		return applicationContext.getBean(name, claz);
	}
	
	/**
	 * 获取Spring Bean
	 * 
	 * @param name
	 * @param claz
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(Class<?> claz) throws BeansException {
		return applicationContext.getBean(claz);
	}
}