package com.jwtexample.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jwtexample.jwt.JWTManager;
import com.jwtexample.model.Login;
import com.jwtexample.model.User;
import com.jwtexample.services.impl.UserServiceImpl;

/**
 * 
 * @author bhawani.singh
 *
 */

@Controller
public class LoginController {
	@Autowired
	UserServiceImpl userService;

	private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("in showLogin");
		return "login";
	}
	
	
	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void loginProcess(HttpServletRequest request, HttpServletResponse response,			
			@RequestBody Login login ) {
		LOGGER.info("in loginProcess");
		User user = userService.validateUser(login);		
		if (null != user) {
			String jwToken = JWTManager.getJWToken(login);
			response.setHeader("Authorization", jwToken);
		}		
	}	
}
