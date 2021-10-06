package com.bookstore.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.app.entities.Author;
import com.bookstore.app.services.AuthorService;

@Controller
@RequestMapping("/authors")
public class AuthorController {
	
	@Autowired
	public AuthorService authorService;
	
	@GetMapping()
	public String listAuthors(ModelMap model) {
		List<Author> authors = authorService.listAll();
		/*List<Author> authors = authorService.listAssets();*/
		model.addAttribute("authors", authors);
		return "authors";
	}
	

}
