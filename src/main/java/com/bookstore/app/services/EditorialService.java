package com.bookstore.app.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookstore.app.entities.Editorial;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.EditorialRepository;

@Service
public class EditorialService {

	private EditorialRepository editorialRepository;

	public void register(String name) throws ServiceError {
		validate(name);
		Editorial editorial = new Editorial();
		editorial.setName(name);
		editorial.setRegister(Boolean.TRUE);

		editorialRepository.save(editorial);
	}

	public void change(String id, String name) throws ServiceError {
		validate(name);

		Optional<Editorial> response = editorialRepository.findById(id);
		if (response.isPresent()) {
			Editorial editorial = response.get();
			editorial.setName(name);

			editorialRepository.save(editorial);
		} else {
			throw new ServiceError("The requested editorial was not found.");
		}
	}

	public void discharge(String id, String name) throws ServiceError {
		validate(name);

		Optional<Editorial> response = editorialRepository.findById(id);
		if (response.isPresent()) {
			Editorial editorial = response.get();
			editorial.setRegister(Boolean.FALSE);
		} else {
			throw new ServiceError("The requested editorial was not found.");
		}
	}

	private void validate(String name) throws ServiceError {
		if (name == null || name.isEmpty()) {
			throw new ServiceError("The editorial´s name cannot be null.");
		}

	}

}
