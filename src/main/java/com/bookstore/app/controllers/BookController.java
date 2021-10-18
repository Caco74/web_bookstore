package com.bookstore.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.app.entities.Author;
import com.bookstore.app.entities.Book;
import com.bookstore.app.entities.Editorial;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.services.AuthorService;
import com.bookstore.app.services.BookService;
import com.bookstore.app.services.EditorialService;

@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	EditorialService editorialService;
	
	@GetMapping("/list")
	public String listBooks(ModelMap model) throws ServiceError {
		try {
			List<Book> books = bookService.listAll();
			model.addAttribute("list_books", books);
			return "books";			
		} catch (ServiceError e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("/register")
	public String register(ModelMap model) throws ServiceError {
		List<Author> authors = authorService.listAll();
		List<Editorial> editorials = editorialService.listAll();
		model.addAttribute("authors_list", authors);
		model.addAttribute("editorial_list", editorials);
		
		return "register_books";
	}
	
	@PostMapping("/register")
	public String registration(ModelMap model, @RequestParam Long isbn, @RequestParam("title") String title, @RequestParam("year") Integer year, @RequestParam("copies") Integer copies, String nameAuthor, String nameEditorial) throws ServiceError {
		try {
			Author author = authorService.finByName(nameAuthor);
			Editorial editorial = editorialService.findByName(nameEditorial);
			bookService.register(isbn, title, year, copies, 0, 0, author, editorial);
			
			return "redirect:/books/list";
		} catch (ServiceError e) {
			return "redirect:/books/list";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id) throws ServiceError {
		Book book = bookService.listBook(id);
		
		model.addAttribute("book", book);
		return "edit_books";
	}
 	
	@PostMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id , @RequestParam String title, @RequestParam Integer copies) throws ServiceError {
		try {
			bookService.change(id, title, copies);
		} catch (ServiceError e) {
			return "redirect:/books/list";
		}
		return "redirect:/books/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable String id) throws ServiceError {
		try {
			bookService.delete(id);
			return "redirect:/books/list";
		} catch (ServiceError e) {
			return "redirect:/books/list";			
		}
	}
	
	@GetMapping("/state/{id}")
	public String state(@PathVariable String id) throws ServiceError {
		try {
			bookService.state(id);
			return "redirect:/books/list";
		} catch (ServiceError e) {
			return "redirect:/books/list";			
		}
	}

}
