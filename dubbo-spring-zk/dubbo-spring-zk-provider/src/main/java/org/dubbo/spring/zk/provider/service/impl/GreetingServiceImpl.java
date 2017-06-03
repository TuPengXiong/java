package org.dubbo.spring.zk.provider.service.impl;

import org.dubbo.spring.zk.inteface.service.GreetingService;

/**
 * GreetingServiceImpl !
 *
 */
public class  GreetingServiceImpl implements GreetingService
{
    public void hello(String something){
    	System.out.println("hello " + something);
    }
}
