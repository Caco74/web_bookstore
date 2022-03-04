package com.bookstore.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bookstore.app.services.ClientService;

@SpringBootApplication
public class BookstoreApplication {

	@Autowired
	private ClientService clientService;
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	// Este método le dice a la configuración de SPRING SECURITY cuál es el servicio que vamos a utilizar para autentificar el cliente.
	// Luego setea un encriptador de contraseñas al servicio de cliente.
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(clientService).passwordEncoder(new BCryptPasswordEncoder());
	
	}
}
