package com.bookstore.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.app.entities.Picture;

public interface PictureRepository extends JpaRepository<Picture, String> {

}
