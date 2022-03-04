package com.bookstore.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.app.entities.Client;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.services.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	@GetMapping("/list")
	public String listClients(ModelMap model) {
		try {
			List<Client> clients = clientService.listAll();
			model.addAttribute("list_clients", clients);
			return "clients";
		} catch (ServiceError e) {
			System.out.println(e.getMessage());
			return "/clients/list";
		}
	}
	
	@GetMapping("/register")
	public String register() {
		return "register_clients";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam MultipartFile file, @RequestParam Long identification, @RequestParam String name, @RequestParam("last_name") String lastName, @RequestParam String phone, @RequestParam String mail, @RequestParam String pass) {
		try {
			clientService.register(file, identification, name, lastName, phone, mail, pass);
			return "redirect:/clients/list";
		} catch (ServiceError e) {
			return "redirect:/clients/list";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id) {
		try {
			Client client = clientService.listClient(id);
			
			model.addAttribute("client", client);
			return "edit_clients";
		} catch (Exception e) {
			return "/clients/list";
		}
	}
	
	@PostMapping("/edit/{id}")
	public String edit(MultipartFile file, @PathVariable("id") String id, @RequestParam Long identification, @RequestParam String name, @RequestParam("last_name") String lastName, @RequestParam String phone, @RequestParam String mail, @RequestParam String pass) throws ServiceError {
		try {
			clientService.change(file, id, identification, name, lastName, phone, mail, pass);
			return "redirect:/clients/list";
		} catch (ServiceError e) {
			return "redirect:/clients/list";
		}
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) throws ServiceError {
		clientService.delete(id);
		return "redirect:/clients/list";
	}
	
	@GetMapping("/state/{id}")
	public String state(@PathVariable("id") String id) throws ServiceError {
		try {
			clientService.state(id);
			return "redirect:/clients/list";			
		} catch (ServiceError e) {
			return "redirect:/clients/list";			
		}
	}

}
 