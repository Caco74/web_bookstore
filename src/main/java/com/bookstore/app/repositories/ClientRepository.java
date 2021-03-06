package com.bookstore.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
	
	@Query("SELECT c FROM Client c WHERE c.register = true")
	public List<Client> searchAssets();
	
	@Query("SELECT c FROM Client c WHERE c.name = :name")
	public Client findByName(@Param("name") String name);
	
	@Query("SELECT c FROM Client c WHERE c.mail = :mail")
	public Client findByEmail(@Param("mail") String mail);
	
}
