package provider.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTestProvider {

	/*@Test
	public void run() throws IOException{
		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.in.read();
	}*/
	
	public static void main(String[] args) throws IOException {
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		System.in.read();
	}
}
