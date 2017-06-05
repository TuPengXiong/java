package provider.test;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTestProvider {

	@Test
	public void run() throws IOException{
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext  cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		cxt.start();
		System.in.read();
	}
}
