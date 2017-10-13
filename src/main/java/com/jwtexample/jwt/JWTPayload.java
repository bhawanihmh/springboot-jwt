package com.jwtexample.jwt;

/**
 * @author bhawani.singh
 *
 * JWT payload
 */
public class JWTPayload {	
	
	private String userName;
	private String password;
	
	
	/**
	 * 
	 */
	public JWTPayload() {
		super();
	}

	/**
	 * 
	 * @param userName
	 * @param password
	 */
	public JWTPayload(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
