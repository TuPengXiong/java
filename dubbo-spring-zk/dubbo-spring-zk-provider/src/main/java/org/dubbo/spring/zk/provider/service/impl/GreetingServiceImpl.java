package org.dubbo.spring.zk.provider.service.impl;

import org.dubbo.spring.zk.inteface.service.GreetingService;

/**
 * GreetingServiceImpl !
 *
 */
public class  GreetingServiceImpl implements GreetingService
{
    public String hello(String something){
    	throw new RuntimeException("hello "+something);
    	/*System.out.println("hello " + something);
    	return  "hello "+something;*/
    }
}
