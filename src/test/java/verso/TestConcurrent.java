package verso;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pojo.Book;
import dao.BookDao;

public class TestConcurrent extends Thread {

	private static BeanFactory context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    
	public static void main(String args[]) {
	    BookDao dao = (BookDao) context.getBean("bookDao");
	    Book book = dao.findByName("Harold Abelson");
	    System.out.println(book);
	}
}
