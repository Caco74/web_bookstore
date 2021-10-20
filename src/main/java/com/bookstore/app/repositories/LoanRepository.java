package com.bookstore.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String>{
	
	@Query("SELECT l FROM Loan l WHERE l.register = true")
	public List<Loan> searchAssets();

}
