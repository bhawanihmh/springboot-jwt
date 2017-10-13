package com.jwtexample.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jwtexample.model.Employee;

/**
 * @author bhawani.singh
 *
 */
@Controller
public class EmployeeConrtoller {

	private static final Logger LOGGER = Logger.getLogger(EmployeeConrtoller.class.getName());
	
	/**
	 * Takes add employee request.
	 * @param request
	 * @return Employee
	 */	
	@RequestMapping(value = "/addemployee", method = RequestMethod.POST)
	@ResponseBody
	public Employee addEmployee(@RequestBody Employee model, 
			HttpServletRequest request) {
		LOGGER.info("Employee Name = " + model.getName());
		LOGGER.info("Employee Salary = " + model.getSalary());
		model.setId("1001");		
		return model;		
	}	
}