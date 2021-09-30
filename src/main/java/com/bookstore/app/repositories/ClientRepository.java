package com.bookstore.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
	

}
