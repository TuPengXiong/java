/** 
 * Project Name:jvm 
 * File Name:RuntimeTest.java 
 * Package Name:com.tupengxiong.jvm 
 * Date:2018年4月9日下午1:37:47 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm;

/** 
 * ClassName:RuntimeTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年4月9日 下午1:37:47 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class RuntimeTest {

	public static void main(String[] args) {
		os();
	}
	public static void os() {
		System.out.println("Java运行时环境版本:\n" + System.getProperty("java.version"));

		System.out.println("Java 运行时环境供应商:\n" + System.getProperty("java.vendor"));

		System.out.println("Java 供应商的URL:\n" + System.getProperty("java.vendor.url"));

		System.out.println("Java安装目录:\n" + System.getProperty("java.home"));

		System.out.println("Java 虚拟机规范版本:\n" + System.getProperty("java.vm.specification.version"));

		System.out.println("Java 类格式版本号:\n" + System.getProperty("java.class.version"));

		System.out.println("Java类路径：\n" + System.getProperty("java.class.path"));

		System.out.println("加载库时搜索的路径列表:\n" + System.getProperty("java.library.path"));

		System.out.println("默认的临时文件路径:\n" + System.getProperty("java.io.tmpdir"));

		System.out.println("要使用的 JIT 编译器的名称:\n" + System.getProperty("java.compiler"));

		System.out.println("一个或多个扩展目录的路径:\n" + System.getProperty("java.ext.dirs"));

		System.out.println("操作系统的名称:\n" + System.getProperty("os.name"));

		System.out.println("操作系统的架构:\n" + System.getProperty("os.arch"));

		System.out.println("操作系统的版本:\n" + System.getProperty("os.version"));

		System.out.println("文件分隔符（在 UNIX 系统中是“/”）:\n" + System.getProperty("file.separator"));

		System.out.println("路径分隔符（在 UNIX 系统中是“:”）:\n" + System.getProperty("path.separator"));

		System.out.println("行分隔符（在 UNIX 系统中是“/n”）:\n" + System.getProperty("line.separator"));

		System.out.println("用户的账户名称:\n" + System.getProperty("user.name"));

		System.out.println("用户的主目录:\n" + System.getProperty("user.home"));

		System.out.println("用户的当前工作目录:\n" + System.getProperty("user.dir"));

		System.out.println("当前的classpath的绝对路径的URI表示法：---->> :\n" + Thread.currentThread().getContextClassLoader().getResource(""));

		System.out.println("得到的是当前类MybatisHelloWorld.class文件的URI目录。不包括自己！:\n" + RuntimeTest.class.getResource(""));
		System.out.println("得到的是当前的classpath的绝对URI路径。:\n" + RuntimeTest.class.getResource("/"));

		//-Xmx512m -Xmx1024m
		System.out.println("Java进程可以向操作系统申请到的最大内存:" + (Runtime.getRuntime().maxMemory()) / (1024 * 1024) + "M");
		System.out.println("Java进程空闲内存:" + (Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "M");
		System.out.println("Java进程现在从操作系统那里已经申请了内存:" + (Runtime.getRuntime().totalMemory()) / (1024 * 1024) + "M");

		byte[] bys = new byte[50* 1024 * 1024];// 申请50M内存
		System.out.println("Java进程可以向操作系统申请到的最大内存:" + (Runtime.getRuntime().maxMemory()) / (1024 * 1024) + "M");
		System.out.println("Java进程空闲内存:" + (Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "M");
		System.out.println("Java进程现在从操作系统那里已经申请了内存:" + (Runtime.getRuntime().totalMemory()) / (1024 * 1024) + "M");
	}
}
  