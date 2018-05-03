package com.lovesher.proxy;

import org.junit.Before;

import com.lovesher.interfaces.HelloService;
import com.lovesher.proxy.support.Extend;
import com.lovesher.proxy.support.CGLib.MethodInterceptor;
import com.lovesher.proxy.support.java.InvocationHandler;

/**
 * 
 * ClassName: ProxyJdkTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018年5月2日 下午5:53:06 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 */
public class ProxyFactoryTest {
	@Before
	public void init() {
		/* 设置此系统属性,让JVM生成的Proxy类写入文件 */
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
	}

	@org.junit.Test
	public void Test() {
		HelloService helloService = (HelloService) ProxyFactory.getCGLibProxy(new Class[] { HelloService.class }, new MethodInterceptor(new HelloService() {

			@Override
			public String sayHello(String name) {
				System.out.println(name);
				return name;
			}
		}, new Extend() {

			@Override
			public void doBefore() {
				System.out.println("CGLibProxy doBefore");
			}

			@Override
			public void doAfter() {
				System.out.println("CGLibProxy doAfter");
			}
		}));
		helloService.sayHello("CGLibProxy");

		
		
		HelloService helloService1 = (HelloService) ProxyFactory.getJavaProxy(new Class[] { HelloService.class }, new InvocationHandler(new HelloService() {

			@Override
			public String sayHello(String name) {
				System.out.println(name);
				return name;
			}
		}, new Extend() {

			@Override
			public void doBefore() {
				System.out.println("JavaProxy doBefore");
			}

			@Override
			public void doAfter() {
				System.out.println("JavaProxy doAfter");
			}
		}));
		helloService1.sayHello("JavaProxy");

	}
}