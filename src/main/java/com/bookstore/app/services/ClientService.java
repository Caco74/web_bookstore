package com.bookstore.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.app.entities.Client;
import com.bookstore.app.entities.Picture;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.repositories.ClientRepository;

@Service
public class ClientService implements UserDetailsService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired PictureService pictureService;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void register(MultipartFile file ,Long identification, String name, String lastName, String phone, String mail, String pass) throws ServiceError {
		validate(identification, name, lastName);
		
		Client client = new Client();
		client.setIdentification(identification);
		client.setName(name);
		client.setLastName(lastName);
		client.setPhone(phone);
		client.setMail(mail);
		String cryptPass = new BCryptPasswordEncoder().encode(pass);
		client.setPass(cryptPass);
		
		client.setRegister(Boolean.TRUE);
		
		Picture picture = pictureService.save(file);
		client.setPicture(picture);
		
		clientRepository.save(client);
		
		notificationService.send("Bienvinido a Biblioteca online", "Biblioteca Online", client.getMail());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void change(MultipartFile file ,String id, Long identification, String name, String lastName, String phone, String mail, String pass) throws ServiceError {
		validate(identification,name, lastName);
		
		Optional <Client> response = clientRepository.findById(id);
		if (response.isPresent()) {
			Client client = response.get();
			client.setIdentification(identification);
			client.setName(name);
			client.setLastName(lastName);
			client.setPhone(phone);
			client.setMail(mail);
			
			String cryptPass = new BCryptPasswordEncoder().encode(pass);
			client.setPass(cryptPass);
			
			String idPic = null;
			if (client.getPicture() != null) {
				idPic = client.getPicture().getId();
			}
			
			Picture pic = pictureService.reload(idPic, file);
			client.setPicture(pic);
					
			
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

	// Crear cliente del dominio SPRING SECURITY
	// Este método es llamado cuando el usuario quiere autentificarse en la plataforma.
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Client client = clientRepository.findByEmail(mail);
		if (client != null) {
			// Listado de permisos que tiene el cliente
			List<GrantedAuthority> permissions = new ArrayList<>();
			
			GrantedAuthority p1 = new SimpleGrantedAuthority("MODULE_LOAN");
			permissions.add(p1);
			
			User user = new User(client.getMail(), client.getPass(), permissions);
			return user;	
		} else {
			return null;			
		}
	}

}
