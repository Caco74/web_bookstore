package com.bookstore.app.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Loan {
	
	@Id
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date loanDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate;
	
	private Boolean register;	
//	private Book book;
//	private Client client;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Boolean getRegister() {
		return register;
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}
	
	


}
