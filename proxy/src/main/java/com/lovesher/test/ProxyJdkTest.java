package com.lovesher.test;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Before;




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
public class ProxyJdkTest {
	@Before
	public void init(){
		/* 设置此系统属性,让JVM生成的Proxy类写入文件 */
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
	}
	@org.junit.Test
	public void Test() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> proxyClass = Proxy.getProxyClass(ProxyJdkTest.class.getClassLoader()
				, IHello.class);
		final Constructor<?> cnos = proxyClass.getConstructor(InvocationHandler.class);
		final InvocationHandler iHandler = new MyInvocationHandler(new Hello());
		IHello hello = (IHello)cnos.newInstance(iHandler);
		hello.sayHello();
	}
	
	public class MyInvocationHandler implements java.lang.reflect.InvocationHandler{
		private Object target;
		public MyInvocationHandler(Object target){
			this.target=target;
		}
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			System.out.println("[before] invoke");	
			Object obj = method.invoke(target, args);
			System.out.println("[after] invoke");
			return obj;
		}
		
	}
	public interface IHello{
		void sayHello();
	}
	public class Hello implements IHello{
		
		@Override
		public void sayHello() {
			System.out.println("exe sayHello()");			
		}
		
	}
}