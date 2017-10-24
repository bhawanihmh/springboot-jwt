package com.jwtexample.jwt;

import org.apache.log4j.Logger;

/**
 * @author bhawani.singh
 *
 */
public class JwtHeaderTokenExtractor {
	
	private static final Logger LOGGER = Logger.getLogger(JwtHeaderTokenExtractor.class.getName());
	
    public static String HEADER_PREFIX = "Bearer ";

    /**
     * token extractor
     * 
     * @param header
     * @return
     */
    public static String extract(String header) {
    	try {
	        if ("".equals(header)) {            
				throw new Exception("Authorization header cannot be blank!");			
	        }
	
	        if (header.length() < HEADER_PREFIX.length()) {
	            throw new Exception("Invalid authorization header size.");
	        }        
    	} catch (Exception e) {
    		LOGGER.error(e);
		}

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
