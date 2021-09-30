package com.bookstore.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String>{

}
