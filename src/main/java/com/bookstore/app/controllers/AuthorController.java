package com.bookstore.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.app.entities.Author;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.services.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {
	
	@Autowired
	public AuthorService authorService;
	
	@GetMapping("/list")
	public String listAuthors(ModelMap model) {
		List<Author> authors = authorService.listAll();
		/*List<Author> authors = authorService.listAssets();*/
		model.addAttribute("list_authors", authors);
		//model.put("active", authorService.)
		return "authors";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register_authors";
	}
	
	@PostMapping("/register")
	public String registration(ModelMap model, @RequestParam  String name) throws ServiceError {
		try {
			authorService.register(name);			
		} catch (ServiceError e) {
			model.put("error", e.getMessage());
			return "register_authors";
		}
		return "redirect:/authors/list";
	}
	
	
	
//	@GetMapping("/list_assets")
//	public String listAuthorsd(ModelMap model) {
//		List<Author> authors = authorService.listAll();
//		/*List<Author> authors = authorService.listAssets();*/
//		model.addAttribute("authors", authors);
//		return "authors";
//	}
	

}
