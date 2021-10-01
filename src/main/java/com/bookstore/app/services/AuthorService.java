package com.bookstore.app.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookstore.app.entities.Author;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.AuthorRepository;



@Service
public class AuthorService {
	private AuthorRepository authorRepository;
	
	public void register(String name) throws ServiceError{
		validate(name);
		Author author = new Author();
		author.setName(name);
		author.setRegister(Boolean.TRUE);
		
		authorRepository.save(author);
	}
	
	public void change(String id, String name) throws ServiceError {
		validate(name);
		
		Optional <Author> response = authorRepository.findById(id); 
		if (response.isPresent()) {
			Author author = response.get();
			author.setName(name);
			
			authorRepository.save(author);
		} else {
			throw new ServiceError("The requested author was not found.");
		}		
	}
	
	public void discharge(String id, String name) throws ServiceError {
		validate(name);
		
		Optional <Author> response = authorRepository.findById(id);
		if (response.isPresent()) {
			Author author = response.get();
			author.setRegister(Boolean.FALSE);
		} else {
			throw new ServiceError("The requested author was not found.");
		}
	}
	
	private void validate(String name) throws ServiceError {
		if (name == null || name.isEmpty()) {
			throw new ServiceError("The author's name cannot be null.");
		}		
	}
	

}
