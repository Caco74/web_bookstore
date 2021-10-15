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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String idAuhtor, ModelMap model) throws ServiceError {
		Author author = authorService.listAuthor(idAuhtor);
		
		model.addAttribute("author", author);
		return "edit_authors";
	}
	
	@PostMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable String id, @RequestParam String name) throws ServiceError {
		try {
			authorService.change(id, name);
		} catch (ServiceError e) {
			return "redirect:/authors/edit/"+id;
		}
		return "redirect:/authors/list";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(ModelMap model, @PathVariable String id) throws ServiceError  {
		try {
			authorService.delete(id);
			return "redirect:/authors/list";
		} catch (ServiceError e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
	}
	
	@GetMapping("/state/{id}")
	public String state(@PathVariable String id) throws ServiceError {
		try {
			authorService.state(id);
			return "redirect:/authors/list";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
	}
	

}
