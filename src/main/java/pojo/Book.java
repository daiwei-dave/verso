package pojo;

import java.sql.Date;

import verso.annotation.Table;
import verso.annotation.Column;

@Table("book")
public class Book {
    @Column("id") 
    Integer id;
    
	@Column("author") 
	Integer authorId;
	
	@Column("name")
	String name;
	
	@Column("comment")
	String comment;
	
	@Column("finishTime")
	Date finishTime;
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	@Override
	public String toString() {
	    return String.format("id=%d, author=%d, name=%s, comment=%s, finishTime=%s", 
	            id, authorId, name, comment, finishTime);
	}
}
