package com.jwtexample.services.impl;


import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.jwtexample.model.Login;
import com.jwtexample.model.User;
import com.jwtexample.services.UserService;

/**
 * 
 * @author bhawani.singh
 *
 */

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	/**
	 * 
	 */
	private static HashMap<String, User> users = new HashMap<String, User>();
	

	/**
	 * 
	 * @param login
	 * @return
	 */
	public User validateUser(Login login) {
		
		LOGGER.info("User count = " + users.size());
		LOGGER.info("User Name = " + login.getUserName());
		
		return users.get(login.getUserName());
	}

	/**
	 * 
	 * @param user
	 */
	public void register(User user) {
		LOGGER.info("Registered User Name = " + user.getUserName());
		users.put(user.getUserName(), user);		
	}

	
	
}
