package com.bookstore.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.bookstore.app.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{
	
	@Query("SELECT b FROM Book b WHERE b.register = true")
	public List<Book> searchAssets();
	
	@Query("SELECT b FROM Book b WHERE b.title = :title")
	public Book findByTitle(@Param("title") String title);
	

}
