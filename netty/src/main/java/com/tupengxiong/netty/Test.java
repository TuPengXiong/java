/** 
 * Project Name:test 
 * File Name:Test.java 
 * Package Name:main.webapp 
 * Date:2017年6月15日下午7:56:17 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.netty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * ClassName:Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月15日 下午7:56:17 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class Test {

	public static void main(String[] args) {
		UserService impl = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(),
				new Class<?>[] { UserService.class }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if ((method.getName().equals("toString")) || (method.getName().equals("hashCode"))
								|| (method.getName().equals("getClass")) || (method.getName().equals("notify"))
								|| (method.getName().equals("notifyAll")) || (method.getName().equals("wait"))) {
							throw new NoSuchMethodError();
						}
						System.out.println("execute:" + method.getName());
						RestTemplate restTemplate = new RestTemplate();
						ResponseEntity<String> resp = restTemplate.exchange("http://baidu.com", HttpMethod.GET, null,
								String.class);
						return resp.getBody();
					}
				});
		impl.setName("1");
		System.out.println(impl.getName());
		System.out.println(impl.getName());
		System.out.println(impl.toString());
	}
}
