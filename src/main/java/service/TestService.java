package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pojo.Book;
import pojo.User;
import dao.BookDao;
import dao.UserDao;

@Service
public class TestService {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserDao userDao;
    
    public void test() {
	    Book book = bookDao.findByName("Harold Abelson");
	    System.out.println(book);
	    for (User user : userDao.display()) {
	        System.out.println(user);
	    }
    }
    @Transactional
    public void insert(String name) {
        User user = new User();
        user.setEmail("mine");
        user.setName(name);
        user.setPassword("test01");
        userDao.insert(user);
        userDao.insert2(user);
        System.out.println("[insert] finsih");
    }
}
