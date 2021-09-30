package com.bookstore.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Client {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private Long identification;
	private String name;
	private String lastName;
	private String phone;
	private Boolean register;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getIdentification() {
		return identification;
	}
	public void setIdentification(Long identification) {
		this.identification = identification;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getRegister() {
		return register;
	}
	public void setRegister(Boolean register) {
		this.register = register;
	}
	
	
	

}
