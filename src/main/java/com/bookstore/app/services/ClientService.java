package com.bookstore.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.app.entities.Client;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.ClientRepository;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void register(Long identification, String name, String lastName, String phone) throws ServiceError {
		validate(identification, name, lastName);
		
		Client client = new Client();
		client.setIdentification(identification);
		client.setName(name);
		client.setLastName(lastName);
		client.setPhone(phone);
		client.setRegister(Boolean.TRUE);
		
		clientRepository.save(client);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void change( String id, Long identification, String name, String lastName, String phone) throws ServiceError {
		validate(identification,name, lastName);
		
		Optional <Client> response = clientRepository.findById(id);
		if (response.isPresent()) {
			Client client = response.get();
			client.setIdentification(identification);
			client.setName(name);
			client.setLastName(lastName);
			client.setPhone(phone);
			
			clientRepository.save(client);			
		} else {
			throw new ServiceError("The requested client was not found.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delete(String id) throws ServiceError {
		Optional<Client> response = clientRepository.findById(id);
		if (response.isPresent()) {
			Client client = response.get();
			clientRepository.delete(client);
		} else {
			throw new ServiceError("The request client was not found.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void state(String id) throws ServiceError {		
		Optional <Client> response = clientRepository.findById(id);
		if (response.isPresent()) {
			Client client = response.get();
			if (client.getRegister()) {
				client.setRegister(Boolean.FALSE);			
			}else {
				client.setRegister(Boolean.TRUE);
			}
		} else {
			throw new ServiceError("The requested client was not found.");
		}
	}
	
	@Transactional(readOnly = true)
	public List<Client> listAll() throws ServiceError {
		return clientRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Client listClient(String id) throws ServiceError {
		Optional<Client> response = clientRepository.findById(id);
		
		if (response.isPresent()) {
			Client client = response.get();
			return client;
		} else {
			throw new ServiceError("The request client was not found.");
		}
	}
	
	@Transactional(readOnly = true)
	public Client findByName(String name) throws ServiceError {
		return clientRepository.findByName(name);
	}
	
	private void validate(Long identification, String name, String lastName) throws ServiceError {
		if (name == null || name.isEmpty()) {
			throw new ServiceError("The client's name cannot be null.");
		}
		if (identification== null) {
			throw new ServiceError("NOOO.");
		}
		if (lastName == null || lastName.isEmpty()) {
			throw new ServiceError("The client's last name cannot be null.");
		}
		
	}

}
