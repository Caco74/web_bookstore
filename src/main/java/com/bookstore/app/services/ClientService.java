package com.bookstore.app.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookstore.app.entities.Client;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.ClientRepository;

@Service
public class ClientService {
	
	private ClientRepository clientRepository;
	
	public void register(Long identidication, String name, String lastName, String phone) throws ServiceError {
		validate(name, lastName);
		
		Client client = new Client();
		client.setIdentification(identidication);
		client.setName(name);
		client.setLastName(lastName);
		client.setPhone(phone);
		client.setRegister(Boolean.TRUE);
		
		clientRepository.save(client);
	}
	
	public void change(String id, String name, String lastName, String phone) throws ServiceError {
		validate(name, lastName);
		
		Optional <Client> response = clientRepository.findById(id);
		if (response.isPresent()) {
			Client client = response.get();
			client.setName(name);
			client.setLastName(lastName);
			client.setPhone(phone);
			
			clientRepository.save(client);			
		} else {
			throw new ServiceError("The requested client was not found.");
		}
	}
	
	public void idscharge(String id, String name, String lastName) throws ServiceError {
		validate(name, lastName);
		
		Optional <Client> response = clientRepository.findById(id);
		if (response.isPresent()) {
			Client client = response.get();
			client.setRegister(Boolean.FALSE);
		} else {
			throw new ServiceError("The requested client was not found.");
		}
	}
	
	private void validate(String name, String lastName) throws ServiceError {
		if (name == null || name.isEmpty()) {
			throw new ServiceError("The client's name cannot be null.");
		}
		if (lastName == null || lastName.isEmpty()) {
			throw new ServiceError("The client's last name cannot be null.");
		}
		
	}

}
