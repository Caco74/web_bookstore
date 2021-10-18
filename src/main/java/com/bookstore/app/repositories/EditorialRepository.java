package com.bookstore.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{
	
	@Query("SELECT e FROM Editorial e WHERE e.register = TRUE")
	public List<Editorial> searchAssets();
	
	@Query("SELECT e FROM Editorial e WHERE e.name = :q")
	public Editorial findByName(@Param("q") String name);

}
