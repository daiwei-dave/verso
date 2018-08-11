package verso.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import verso.session.VSessionFactory;

public class BaseTest {

	private static BeanFactory factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    
	public static void main(String args[]) {
		System.out.println("init start");
		VSessionFactory sessionFactory = (VSessionFactory) factory.getBean("sessionFactory");
		System.out.println("init end");
	}
}
