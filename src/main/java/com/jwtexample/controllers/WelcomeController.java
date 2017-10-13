package com.jwtexample.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	private static final Logger LOGGER = Logger.getLogger(WelcomeController.class.getName());
	
	@RequestMapping("/")
	public String welcome() {		
		return "welcome";
	}

}