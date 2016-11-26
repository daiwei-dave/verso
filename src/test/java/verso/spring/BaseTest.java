package verso.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import verso.session.VSessionFactory;

public class BaseTest {

	private static BeanFactory factory = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    
	public static void main(String args[]) {
		VSessionFactory sessionFactory = (VSessionFactory) factory.getBean("sessionFactory");	
	}
}
