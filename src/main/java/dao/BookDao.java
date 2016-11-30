package dao;

import pojo.Book;
import verso.annotation.Operation;

public interface BookDao {
	@Operation("select * from book b left join person p on b.author=p.id where p.name={0}")
	Book findByName(String name);
}
