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

import com.bookstore.app.entities.Editorial;
import com.bookstore.app.errors.ServiceError;
import com.bookstore.app.services.EditorialService;

@Controller
@RequestMapping("/editorials")
public class EditorialController {
	
	@Autowired
	public EditorialService editorialService;
	
	@GetMapping("/list")
	public String listEditorials(ModelMap model) throws ServiceError {
		List<Editorial> editorials = editorialService.listAll();
		model.addAttribute("list_editorials", editorials);
		return "editorials";
	}
	
	@GetMapping("/register")
	public String registration() throws ServiceError {
		return "register_editorials";
	}
	
	@PostMapping("/register")
	public String registration(ModelMap model,@RequestParam String name) throws ServiceError {
		try {
			editorialService.register(name);
			return "redirect:/editorials/list";
		} catch (ServiceError e) {
			model.put("error", e.getMessage());
			return "register_editorials";
		}		
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap model) throws ServiceError {
		Editorial editorial = editorialService.listEditorial(id);
		
		model.addAttribute("editorial", editorial);
		return "edit_editorials";
	}
	
	@PostMapping("/edit/{id}")
	public String edit(ModelMap model, @PathVariable("id") String id, @RequestParam String name) throws ServiceError {
		try {
			editorialService.change(id, name);
			//model.addAttribute("error", error);
			return "redirect:/editorials/list";
		} catch (ServiceError e) {
			return "redirect:/editorials/edit/"+id;
		}		
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) throws ServiceError {
		try {
			editorialService.delete(id);
			return "redirect:/editorials/list";
		} catch (ServiceError e) {
			return "redirect:/";
		}
	}
	
	@GetMapping("/state/{id}")
	public String state(@PathVariable("id") String id) throws ServiceError {
		try {
			editorialService.state(id);
			return "redirect:/editorials/list";
		} catch (ServiceError e) {
			return "redirect:/";
		}
	}
	

}
