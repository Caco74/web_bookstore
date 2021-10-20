package com.bookstore.app.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.app.entities.Book;
import com.bookstore.app.entities.Client;
import com.bookstore.app.entities.Loan;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.services.AuthorService;
import com.bookstore.app.services.BookService;
import com.bookstore.app.services.ClientService;
import com.bookstore.app.services.LoanService;

@Controller
@RequestMapping("/loans")
public class LoanController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	ClientService clientService;
	
	@GetMapping("/list")
	public String listLoans(ModelMap model) throws ServiceError {
		try {
			List<Loan> loans = loanService.listAll();
			model.addAttribute("list_loans", loans);
			return "loans";			
		} catch (ServiceError e) {
			return e.getMessage();
		}
	}
	
	@GetMapping("/register")
	public String registration(ModelMap model) throws ServiceError {
		List<Book> books = bookService.listAll();
		List<Client> clients = clientService.listAll();
		model.addAttribute("books_list", books);
		model.addAttribute("clients_list", clients);		
		model.addAttribute("localDate", LocalDate.now());
		model.addAttribute("tomorrow", LocalDate.now().plusDays(1));
		return "register_loan";
	}
	
	@PostMapping("/register")
	public String registration(ModelMap model, @RequestParam("loanDate") Date loanDate, @RequestParam("returnDate") Date returnDate , @RequestParam String titleBook, @RequestParam String nameClient ) throws ServiceError {
		try {
			Client clienta = clientService.findByName(nameClient);
			Book book = bookService.findByTitle(titleBook);
			loanService.register(loanDate, returnDate, book, clienta);
			
			return "redirect:/loans/list";
		} catch (ServiceError e) {
			return "redirect:/loans/list";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id) throws ServiceError {
		Loan loan = loanService.listLoan(id);
		List<Book> books = bookService.listAll();
		List<Client> clients = clientService.listAll();
		model.addAttribute("books_list", books);
		model.addAttribute("clients_list", clients);
		model.addAttribute("loan_data", loan);
		return "edit_loans";
	}
	
	

}
