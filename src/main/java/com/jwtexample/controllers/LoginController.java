package com.jwtexample.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("in showLogin");
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new Login());
		return mav;
	}

	@RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("login") Login login) {
		ModelAndView mav = null;
		User user = userService.validateUser(login);	
		
		if (null != user) {
			mav = new ModelAndView("addemployee");
			//mav.addObject("message", "Welcome dear "+ user.getFirstName());
			String jwToken = JWTManager.getJWToken(login);			
		} else {
			mav = new ModelAndView("login");
			mav.addObject("message", "Username or Password is wrong!! Please register Firest.");
		}
		
		
		/*localStorage.jwt = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ';

		function jwtRequest(url, token){
		    var req = new XMLHttpRequest();
		    req.open('get', url, true);
		    req.setRequestHeader('Authorization','Bearer '+token);
		    req.send();
		}

		jwtRequest('/api/example', localStorage.jwt);*/
		
		
		return mav;
	}
}
