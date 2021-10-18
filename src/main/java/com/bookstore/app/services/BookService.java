package com.bookstore.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.entities.Author;
import com.bookstore.app.entities.Book;
import com.bookstore.app.entities.Editorial;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void register(Long isbn, String title, Integer year, Integer copies, @DefaultValue("0") Integer borrowedCopies, @DefaultValue("0") Integer remainingCopies, Author author, Editorial editorial) throws ServiceError {
		
		validate(title, copies);
		
		Book book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setYear(year);
		book.setCopies(copies);
		book.setRegister(Boolean.TRUE);
		book.setBorrowedCopies(borrowedCopies);
		book.setRemainingCopies(remainingCopies);
		book.setAuthor(author);
		book.setEditorial(editorial);
		System.out.println(author);
		System.out.println(editorial);
		
		
		bookRepository.save(book);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void change(String id, String title, Integer copies) throws ServiceError {
		validate(title, copies);
		
		Optional <Book> response = bookRepository.findById(id);
		if (response.isPresent()) {
			Book book = response.get();
			book.setTitle(title);
			book.setCopies(copies);
			
			bookRepository.save(book);
		} else {
			throw new ServiceError("The requested book was not found.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delete(String id) throws ServiceError {
		Optional<Book> response = bookRepository.findById(id);
		if (response.isPresent()) {
			Book book = response.get();
			bookRepository.delete(book);
		} else {
			throw new ServiceError("The request book was not found.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void state(String id) throws ServiceError {
		Optional<Book> response = bookRepository.findById(id);
		if (response.isPresent()) {
			Book book = response.get();
			if (book.getRegister()) {
				book.setRegister(Boolean.FALSE);
			} else {
				book.setRegister(Boolean.TRUE);
			}
		} else {
			throw new ServiceError("The request book was not found.");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Book> listAll() throws ServiceError {
		return bookRepository.findAll();		
	}
	
	@Transactional(readOnly = true)
	public Book listBook(String id) throws ServiceError {
		Optional<Book> response = bookRepository.findById(id);
		if (response.isPresent()) {
			Book book = response.get();
			return book;
		} else {
			throw new ServiceError("No book found.");
		}
	}
	
	private void validate(String title, Integer copies ) throws ServiceError {
		if (title == null || title.isEmpty()) {
			throw new ServiceError("The title of the book can not be null.");
		}
		if (copies == 0 || copies<0 ) {
			throw new ServiceError("You must assign a value greater than zero.");
		}
		
	}

}
