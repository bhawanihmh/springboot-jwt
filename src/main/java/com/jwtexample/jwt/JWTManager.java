package com.jwtexample.jwt;

import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.jwtexample.model.Login;

/**
 * 
 * @author bhawani.singh
 *
 */
public class JWTManager {

	private static final Logger LOGGER = Logger.getLogger(JWTManager.class.getName());
	
	public static final String key = "1234";
	public static final String signatureKey = JWTUtil.generateSignatureKey();
	public static final SecretKey secretKey = JWTUtil.getKey(key);
	
	private static ArrayList<String> jwTokens = new ArrayList<String>();

	/**
	 * Get JW token 
	 * 
	 * @param login
	 * @return
	 */
	public static synchronized String getJWToken(Login login) {		
		JWTPayload jwtPayload = new JWTPayload(login.getUserName(), login.getPassword());
		JWToken jsonWebToken = new JWToken(jwtPayload, "id", System.currentTimeMillis(), "subject", "issuer",null);
		String jwToken = JWTUtil.createJWT(jsonWebToken, signatureKey, secretKey);
		LOGGER.info("==============   jwToken : " + jwToken);		
		jwTokens.add(jwToken);		
		return jwToken;		
	}
	
	
	/**
	 * Validate json web token 
	 * 
	 * @param jwToken
	 * @return
	 */
	public static synchronized boolean validateJWToken(String jwToken) {
		
		if(jwTokens.contains(jwToken)){
			JWTUtil.parseJWT(jwToken, secretKey, signatureKey);
			return true;			
		}
		return false;
	}
	

	/**
	 * Added main method to test JWT.
	 * 
	 * @param args
	 */
	public static void main1(String[] args) {

		String key = "1234";
		ObjectMapper mapper = new ObjectMapper();
		JWToken jsonWebToken = null;
		String jwToken = null;

		String signatureKey = JWTUtil.generateSignatureKey();
		SecretKey secretKey = JWTUtil.getKey(key);

		String workingDir = System.getProperty("user.dir");
		LOGGER.info("workingDir : " + workingDir);

		/*
		 * String filePath = workingDir +
		 * "\\src\\main\\resources\\jsonWebToken.json"; jsonWebToken =
		 * mapper.readValue(new File(filePath), JWToken.class);
		 */

		/*
		 * { "payload": { "userName": "bhawani@gmail.com", "password":
		 * "Bhawani@12123" }, "jti": "id", "iat": 1483083638000, "sub":
		 * "subject", "iss": "issuer", "exp": 1483227638000 }
		 */

		JWTPayload jwtPayload = new JWTPayload("bhawani@gmail.com", "Bhawani@12123");

		/*jsonWebToken = new JWToken(jwtPayload, "id", System.currentTimeMillis(), "subject", "issuer",
				System.currentTimeMillis() + (30 * 100));*/
		
		jsonWebToken = new JWToken(jwtPayload, "id", System.currentTimeMillis(), "subject", "issuer",
				null);

		LOGGER.info("signatureKey : " + signatureKey);
		LOGGER.info("Encrypted string : " + jwToken);
		
		jwToken = JWTUtil.createJWT(jsonWebToken, signatureKey, secretKey);

		LOGGER.info("==============   jwToken : " + jwToken);		

		JWTUtil.parseJWT(jwToken, secretKey, signatureKey);

	}
}
