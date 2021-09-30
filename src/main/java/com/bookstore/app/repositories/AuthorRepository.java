package com.bookstore.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String>{
	

}
