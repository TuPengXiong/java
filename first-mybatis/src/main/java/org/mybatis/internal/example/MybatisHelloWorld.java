package org.mybatis.internal.example;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.internal.example.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MybatisHelloWorld {
	
	private static Logger logger = LoggerFactory.getLogger(MybatisHelloWorld.class);
	public static void main(String[] args) {
		getUser();
		//memoryMonitor();
		//os();
	}

	static void getUser() {
		BaseExecutor a;
		
		String resource = "org/mybatis/internal/example/Configuration.xml";
		Reader reader;
		try {
			// 加载文件流
			reader = Resources.getResourceAsReader(resource);
			// 获取sqlSession
			SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			// 开启会话
			SqlSession session = sqlMapper.openSession();
			try {
				//查询会1级缓存
				User user = (User) session.selectOne("org.mybatis.internal.example.mapper.UserMapper.getUser", 1);
				logger.info(user.getLfPartyId() + "," + user.getPartyName());
				Thread.sleep(20000);
				user = (User) session.selectOne("org.mybatis.internal.example.mapper.UserMapper.getUser", 1);
				logger.info(user.getLfPartyId() + "," + user.getPartyName());
				
				user = (User) session.selectOne("org.mybatis.internal.example.mapper.UserMapper.getUser", 2);
				logger.info(user.getLfPartyId() + "," + user.getPartyName());
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			} finally {
				session.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void memoryMonitor() {
		for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
			Thread thread = entry.getKey();
			StackTraceElement[] stackTraceElements = entry.getValue();

			if (thread.equals(Thread.currentThread())) {
				continue;
			}
			logger.info("\n线程： " + thread.getName() + "\n");
			for (StackTraceElement element : stackTraceElements) {
				logger.info("\t" + element + "\n");
			}
		}
	}

	public static void os() {
		logger.info("Java运行时环境版本:\n" + System.getProperty("java.version"));

		logger.info("Java 运行时环境供应商:\n" + System.getProperty("java.vendor"));

		logger.info("Java 供应商的URL:\n" + System.getProperty("java.vendor.url"));

		logger.info("Java安装目录:\n" + System.getProperty("java.home"));

		logger.info("Java 虚拟机规范版本:\n" + System.getProperty("java.vm.specification.version"));

		logger.info("Java 类格式版本号:\n" + System.getProperty("java.class.version"));

		logger.info("Java类路径：\n" + System.getProperty("java.class.path"));

		logger.info("加载库时搜索的路径列表:\n" + System.getProperty("java.library.path"));

		logger.info("默认的临时文件路径:\n" + System.getProperty("java.io.tmpdir"));

		logger.info("要使用的 JIT 编译器的名称:\n" + System.getProperty("java.compiler"));

		logger.info("一个或多个扩展目录的路径:\n" + System.getProperty("java.ext.dirs"));

		logger.info("操作系统的名称:\n" + System.getProperty("os.name"));

		logger.info("操作系统的架构:\n" + System.getProperty("os.arch"));

		logger.info("操作系统的版本:\n" + System.getProperty("os.version"));

		logger.info("文件分隔符（在 UNIX 系统中是“/”）:\n" + System.getProperty("file.separator"));

		logger.info("路径分隔符（在 UNIX 系统中是“:”）:\n" + System.getProperty("path.separator"));

		logger.info("行分隔符（在 UNIX 系统中是“/n”）:\n" + System.getProperty("line.separator"));

		logger.info("用户的账户名称:\n" + System.getProperty("user.name"));

		logger.info("用户的主目录:\n" + System.getProperty("user.home"));

		logger.info("用户的当前工作目录:\n" + System.getProperty("user.dir"));

		logger.info("当前的classpath的绝对路径的URI表示法：---->> :\n" + Thread.currentThread().getContextClassLoader().getResource(""));

		logger.info("得到的是当前类MybatisHelloWorld.class文件的URI目录。不包括自己！:\n" + MybatisHelloWorld.class.getResource(""));
		logger.info("得到的是当前的classpath的绝对URI路径。:\n" + MybatisHelloWorld.class.getResource("/"));

		//-Xmx512m -Xmx1024m
		logger.info("Java进程可以向操作系统申请到的最大内存:" + (Runtime.getRuntime().maxMemory()) / (1024 * 1024) + "M");
		logger.info("Java进程空闲内存:" + (Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "M");
		logger.info("Java进程现在从操作系统那里已经申请了内存:" + (Runtime.getRuntime().totalMemory()) / (1024 * 1024) + "M");

		byte[] bys = new byte[50* 1024 * 1024];// 申请50M内存
		logger.info("Java进程可以向操作系统申请到的最大内存:" + (Runtime.getRuntime().maxMemory()) / (1024 * 1024) + "M");
		logger.info("Java进程空闲内存:" + (Runtime.getRuntime().freeMemory()) / (1024 * 1024) + "M");
		logger.info("Java进程现在从操作系统那里已经申请了内存:" + (Runtime.getRuntime().totalMemory()) / (1024 * 1024) + "M");
	}
}