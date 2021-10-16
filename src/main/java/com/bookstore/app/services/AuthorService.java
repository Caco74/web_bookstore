package com.bookstore.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.entities.Author;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.AuthorRepository;



@Service
public class AuthorService implements UserDetailsService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void register(String name) throws ServiceError{
		validate(name);
		Author author = new Author();
		author.setName(name);
		author.setRegister(Boolean.TRUE);
		
		authorRepository.save(author);		
	}
	
	 
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
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
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void delete(String id) throws ServiceError {		
		Optional <Author> response = authorRepository.findById(id);
		if (response.isPresent()) {
			Author author = response.get();
			authorRepository.delete(author);
		} else {
			throw new ServiceError("The requested author was not found.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void state(String id) throws ServiceError {		
		Optional <Author> response = authorRepository.findById(id);
		if (response.isPresent()) {
			Author author = response.get();
			if (author.getRegister()) {
				author.setRegister(Boolean.FALSE);				
			} else {
				author.setRegister(Boolean.TRUE);				
			}
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
