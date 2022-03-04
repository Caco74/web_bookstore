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
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.app.entities.Author;
import com.bookstore.app.entities.Picture;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.AuthorRepository;



@Service
public class AuthorService implements UserDetailsService{
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PictureService pictureService;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void register(MultipartFile file ,String name) throws ServiceError{
		validate(name);
		Author author = new Author();
		author.setName(name);
		author.setRegister(Boolean.TRUE);
		
		Picture picture = pictureService.save(file);
		author.setPicture(picture);
		
		authorRepository.save(author);		
	}	 
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void change(MultipartFile file, String id, String name) throws ServiceError {
		validate(name);
		
		Optional <Author> response = authorRepository.findById(id); 
		if (response.isPresent()) {
			Author author = response.get();
			author.setName(name);
			
			String idPciture = null;
			if (author.getPicture() != null) {
				idPciture = author.getPicture().getId();
			}
			
			Picture picture = pictureService.reload(idPciture, file);
			author.setPicture(picture);
			
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
	public Author finByName(String name) {
		return authorRepository.findByName(name);		
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
	
	//El método recibe el nombre del cliente y lo busca en el repositorio. Luego lo transforma en un usuario de SPRING SECURITY
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	

}
