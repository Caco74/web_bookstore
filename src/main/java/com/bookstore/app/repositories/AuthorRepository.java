package com.bookstore.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String>{

	@Query("SELECT a FROM Author a WHERE a.register = true")
	public List<Author> searchAssets();
	
	@Query("SELECT COUNT(a) > 0 FROM Author a WHERE a.name = :name")
	public Boolean existByName(@Param("name") String name);

}
