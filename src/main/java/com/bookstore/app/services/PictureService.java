package com.bookstore.app.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.app.entities.Picture;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.PictureRepository;

@Service
public class PictureService {
	
	@Autowired
	private PictureRepository pictureRepository;
	
	// Si el método se ejecuta sin lanzar una excepciones, entonces hace un commit a la base de datos y se aplican todos los cambios.
	// Si el método lanza una excepción y no es atrapada se vuelve atrás con la transacción y no se aplica nada en la base de datos.
	@Transactional
	public Picture save(MultipartFile file) throws ServiceError {
		if (file != null) {
			try {
				Picture picture = new Picture();
				picture.setMime(file.getContentType()); // getContentType() nos va a devolver el tipo mime del archivo que viene adjunto
				picture.setName(file.getName());
				picture.setContenido(file.getBytes()); // Pedimos que pase el contenido de ese archivo a un arreglo de bytes.
				
				return pictureRepository.save(picture);				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}			
		}
		
		return null;		
	}
	
	@Transactional
	public Picture reload(String id, MultipartFile file) throws ServiceError {
		if (file != null) {
			try {
				Picture picture = new Picture();
				
				if (id != null) {
					Optional<Picture> response = pictureRepository.findById(id);
					if (response.isPresent()) {
						picture = response.get();
						
					}
				}
				
				picture.setMime(file.getContentType());
				picture.setName(file.getName());
				picture.setContenido(file.getBytes());
				
				return pictureRepository.save(picture);				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}			
		}

		return null;
	}
}
