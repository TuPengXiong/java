package consumer.test;


import org.dubbo.spring.zk.inteface.service.GreetingService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitTestConsumer {

	@Test
	public void run() {
		@SuppressWarnings("resource")
		ApplicationContext cxt = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
		GreetingService greetingService = (GreetingService) cxt.getBean("greetingService");
		try{
			System.out.println(greetingService.hello("I am consumer"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
