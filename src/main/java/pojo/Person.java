package pojo;

import verso.annotation.Table;
import verso.annotation.Column;

@Table("person")
public class Person {
    
    @Column("id")
	Integer id;
    
    @Column("name")
	String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
