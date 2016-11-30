package verso;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.TestService;

public class TestConcurrent extends Thread {

	private static BeanFactory context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    
	public static void main(String args[]) {
	    TestService service = (TestService) context.getBean("testService");
	    service.insert("123123");
	    service.test();
	}
}
