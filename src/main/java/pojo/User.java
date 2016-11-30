package pojo;

import verso.annotation.Column;
import verso.annotation.Table;

@Table("user")
public class User {
    
    @Column("id")
	Integer id;
    
    @Column("name")
	String name;
    
    @Column("passport")
	String password;
    
    @Column("flag")
	String flag;
    
    @Column("email")
	String email;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
	    return String.format("[User] id=%d, name=%s, password=%s, flag=%s, email=%s", 
	            id, name, password, flag, email);
	}
}
