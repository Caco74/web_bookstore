package com.bookstore.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private JavaMailSender mailSender;
	
	//Asíncrono: el hilo de ejecución no espera a que se termine de enviar el mail, lo ejecuta en un hilo paralelo
	//Por eso el usuario tiene respuesta inmediata, no tiene que esperar a que se termine de enviar el mail.
	//La operación de enviar el mail requiere tiempo y es probable que genere esperas inactivas del usuario generando poca usabilidad y una mala experiencia de usuario.
	@Async
	public void send(String body, String title, String mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mail);
		message.setFrom("francodemetrio87@gmail.com");
		message.setSubject(title);
		message.setText(body);
		
		mailSender.send(message);
	}
	
}
