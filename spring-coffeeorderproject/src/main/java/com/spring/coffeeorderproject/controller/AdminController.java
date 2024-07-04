package com.spring.coffeeorderproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	
	@GetMapping(path = { "/admin-home" })
	public String adminHomeForm() {
		
		
		return "";
	}
	
}
