package com.bookstore.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.entities.Author;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.AuthorRepository;



@Service
public class AuthorService implements UserDetailsService{
	
	@Autowired
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
	
	@Transactional(readOnly = true)
	public Author listAuthor(String id) throws ServiceError {
		Optional<Author> response = authorRepository.findById(id);
		if (response.isPresent()) {
			Author author = response.get();
			return author;
		} else {
			throw new ServiceError("No author found.");
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<Author> listAssets() {
		return authorRepository.searchAssets();
	}
	
	@Transactional(readOnly = true)
	public List<Author> listAll() {
		return authorRepository.findAll();
	}
	
	private void validate(String name) throws ServiceError {
		if (name == null || name.isEmpty()) {
			throw new ServiceError("The author's name cannot be null.");
		}
		
		if	(authorRepository.existByName(name)) {
			throw new ServiceError("The author is already loaded");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	

}
