package com.jwtexample.services;

import org.springframework.stereotype.Service;

import com.jwtexample.model.Login;
import com.jwtexample.model.User;

/**
 * 
 * @author bhawani.singh
 *
 */

@Service
public interface UserService {

	public User validateUser(Login login);

	public void register(User user);

}
