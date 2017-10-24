package com.jwtexample.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jwtexample.jwt.JWTManager;
import com.jwtexample.jwt.JwtHeaderTokenExtractor;
import com.jwtexample.model.Employee;


/**
 * @author bhawani.singh
 *
 */
@Controller
public class EmployeeConrtoller {

	private static final Logger LOGGER = Logger.getLogger(EmployeeConrtoller.class.getName());
		
	@RequestMapping(value = "/showaddemployee", method = RequestMethod.GET)
	public ModelAndView showaddemployee(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("in showaddemployee");
		ModelAndView mav = new ModelAndView("addemployee");		
		return mav;
	}	
	
	/**
	 * Takes add employee request.
	 * @param request
	 * @return Employee
	 */	
	@RequestMapping(value = "/addemployee", method = RequestMethod.POST)
	@ResponseBody
	public Employee addEmployee(@RequestBody Employee model, 
			HttpServletRequest request) {
		
		String accessToken = JwtHeaderTokenExtractor.extract(request.getHeader("Authorization"));
				
		if(JWTManager.validateJWToken(accessToken)){
			LOGGER.info("accessToken is valid");
		}
		
		LOGGER.info("Employee accessToken = " + accessToken);
		LOGGER.info("Employee Name = " + model.getName());
		LOGGER.info("Employee Salary = " + model.getSalary());
		model.setId("1001");		
		return model;		
	}	
}
