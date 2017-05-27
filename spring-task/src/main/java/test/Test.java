package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Test {

	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext("classpath*:/applicationContext.xml");
		
	}
}
