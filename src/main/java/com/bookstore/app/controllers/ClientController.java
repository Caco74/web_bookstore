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

import com.bookstore.app.entities.Client;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.services.ClientService;

@Controller
@RequestMapping("/clients")
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	@GetMapping("/list")
	public String listClients(ModelMap model) throws ServiceError {
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
	public String register() throws ServiceError {
		return "register_clients";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam Long identification, @RequestParam String name, @RequestParam("last_name") String lastName, @RequestParam String phone) throws ServiceError {
		try {
			clientService.register(identification, name, lastName, phone);
			return "redirect:/clients/list";
		} catch (ServiceError e) {
			return "redirect:/clients/list";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id) throws ServiceError {
		Client client = clientService.listClient(id);
		
		model.addAttribute("client", client);
		return "edit_clients";		
	}
	
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, @RequestParam Long identification, @RequestParam String name, @RequestParam("last_name") String lastName, @RequestParam String phone) throws ServiceError {
		try {
			clientService.change(id, name, lastName, phone);
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
 