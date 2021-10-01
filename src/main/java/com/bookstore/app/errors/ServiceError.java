package com.bookstore.app.errors;

public class ServiceError extends Exception {
	
	public ServiceError(String msn) {
		super(msn);
	}
}
