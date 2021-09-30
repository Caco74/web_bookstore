package com.bookstore.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Book {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private Long isbn;
	private String title;
	private Integer age;
	private Integer copies;
	private Integer borrowedCopies;
	private Integer remainingCopies;
	private Boolean register;
	
	@OneToOne
	private Author author;
	
	@OneToOne
	private Editorial editorial;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getIsbn() {
		return isbn;
	}
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	public Integer getBorrowedCopies() {
		return borrowedCopies;
	}
	public void setBorrowedCopies(Integer borrowedCopies) {
		this.borrowedCopies = borrowedCopies;
	}
	public Integer getRemainingCopies() {
		return remainingCopies;
	}
	public void setRemainingCopies(Integer remainingCopies) {
		this.remainingCopies = remainingCopies;
	}
	public Boolean getRegister() {
		return register;
	}
	public void setRegister(Boolean register) {
		this.register = register;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Editorial getEditorial() {
		return editorial;
	}
	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}
	
	
}
