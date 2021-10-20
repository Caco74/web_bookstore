package com.bookstore.app.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.entities.Book;
import com.bookstore.app.entities.Client;
import com.bookstore.app.entities.Loan;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.LoanRepository;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class} )
	public void register(Date loanDate, Date returnDate, Book book, Client client) throws ServiceError{
//		validate(book, client);
		
		Loan loan = new Loan();
		loan.setLoanDate(loanDate);
		loan.setReturnDate(returnDate);
		loan.setBook(book);
		loan.setClient(client);
		loan.setRegister(Boolean.TRUE);
		
		loanRepository.save(loan);
	}
	
	@Transactional(readOnly = true)
	public List<Loan> listAll() throws ServiceError {
		return loanRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Loan listLoan(String id) throws ServiceError {
		Optional<Loan> response = loanRepository.findById(id);
		
		if (response.isPresent()) {
			Loan loan = response.get();
			return loan;
		} else {
			throw new ServiceError("The request loan was not found.");
		}
	}
	
	public void validate() throws ServiceError {
//		if (client.getLastName()) {
//			throw new ServiceError("The client is null");
//		}
		
//		if (book.equals(null)) {
//			throw new ServiceError("The book is null");
//		}
	}

}
