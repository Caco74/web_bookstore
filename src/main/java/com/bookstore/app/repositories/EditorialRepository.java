package com.bookstore.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.app.entities.Editorial;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, String>{

}
