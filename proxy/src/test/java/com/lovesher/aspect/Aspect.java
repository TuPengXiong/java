/** 
 * Project Name:proxy 
 * File Name:Aspect.java 
 * Package Name:com.lovesher.aspect 
 * Date:2018年5月3日下午1:29:27 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * ClassName:Aspect <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年5月3日 下午1:29:27 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect{

	@Pointcut("execution(* com.lovesher.interfaces.HelloService.*(..))")
	public void aspectHelloService() {
	}
	
    @Before("aspectHelloService()")
    public void aspectHelloServiceBefore() {
        System.out.println("aspectHelloServiceBefore........");
    }
    
    @After("aspectHelloService()")
    public void aspectHelloServiceAfter() {
        System.out.println("aspectHelloServiceAfter......");
    }
    
    /**
     *任何目标对象对应的类型持有Secure注解的类方法；
     *必须是在目标对象上声明这个注解，在接口上声明的对它不起作用 
     ***/
    @Pointcut("@within(com.lovesher.annotation.IPort)")
	public void aspectHelloServiceWithin() {
	}
    
    @Before("aspectHelloServiceWithin()")
    public void aspectHelloServiceWithinBefore() {
        System.out.println("aspectHelloServiceWithinBefore........");
    }
    
    @After("aspectHelloServiceWithin()")
    public void aspectHelloServiceWithinAfter() {
        System.out.println("aspectHelloServiceWithinAfter......");
    }
    
    
    /**
     *当前目标对象（非AOP对象）实现了 HelloService接口的任何方法 
     **/
    @Pointcut("target(com.lovesher.interfaces.HelloService)")
	public void aspectHelloServiceTarget() {
	}
    
    @Before("aspectHelloServiceTarget()")
    public void aspectHelloServiceTargetBefore() {
        System.out.println("aspectHelloServiceTargetBefore........");
    }
    
    @Around("aspectHelloServiceTarget()")
    public void aspectHelloServiceTargetAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aspectHelloServiceTargetAround START........");
        System.out.println("aspectHelloServiceTargetAround:"+proceedingJoinPoint.proceed());
        System.out.println("aspectHelloServiceTargetAround END........");
    }
    
    @After("aspectHelloServiceTarget()")
    public void aspectHelloServiceTargetAfter() {
        System.out.println("aspectHelloServiceTargetAfter......");
    }
    
}
