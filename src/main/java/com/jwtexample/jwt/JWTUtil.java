package com.jwtexample.jwt;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

/**
 * @author bhawani.singh
 *
 *         JWT utility
 */
public class JWTUtil {

	private static final Logger LOGGER = Logger.getLogger(JWTUtil.class.getName());

	/**
	 * key map for secret keys
	 */
	private static HashMap<String, SecretKey> keyMap = new HashMap<String, SecretKey>();

	private static final String ALGORITHM = "AES";
	private static final String ALGORITHM_EAS = "AES/ECB/PKCS5Padding";
	private static final String PAYLOAD = "payload";

	/**
	 * Get Secret key
	 * 
	 * @param key
	 * @return
	 */
	public static SecretKey getKey(String key) {
		if (key != null && !key.trim().isEmpty()) {
			SecretKey secretKey = keyMap.get(key);
			if (secretKey == null) {
				byte[] keyBytes = new byte[16];
				java.math.BigInteger b = new java.math.BigInteger(key, 16);
				byte[] bigBytes = b.toByteArray();
				System.arraycopy(bigBytes, 0, keyBytes, 0, Math.min(keyBytes.length, bigBytes.length));
				secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
				keyMap.put(key, secretKey);
			}
			return secretKey;
		} else {
			return null;
		}
	}

	/**
	 * Method copied from AESCryptoUtil
	 * 
	 * @param algorithmAES
	 * @param strToEncrypt
	 * @param secretKey
	 * @return
	 */
	public static String encrypt(String algorithmAES, String strToEncrypt, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(algorithmAES);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final String encryptedString = new String(Base64.encodeBase64(cipher.doFinal(strToEncrypt.getBytes())));
			return encryptedString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Generate signature key
	 * 
	 * @return
	 */
	public static String generateSignatureKey() {
		SecretKey signatureKey = null;
		try {
			signatureKey = KeyGenerator.getInstance(ALGORITHM).generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// get base64 encoded version of the key
		String encodedKey = java.util.Base64.getEncoder().encodeToString(signatureKey.getEncoded());
		return encodedKey;
	}
	
	/**
	 * Generate json web token
	 * 
	 * @param token
	 * @param signatureKey
	 * @param secretKey
	 * @return
	 */
	public static String createJWT(JWToken token, String signatureKey, SecretKey secretKey) {

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signatureKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		DefaultClaims claims = new DefaultClaims();
		JWTPayload payload = token.getPayload();	

		JWTPayload rpload = new JWTPayload();
		rpload.setUserName(payload.getUserName());
		rpload.setPassword(payload.getPassword());

		try {
			String payloadAsString = new ObjectMapper().writeValueAsString(rpload);
			payloadAsString = encrypt(ALGORITHM_EAS, payloadAsString, secretKey);
			claims.put(PAYLOAD, payloadAsString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Date expirationDate = token.getExp() == null? null: new Date(token.getExp());
		
		claims.setId(token.getJti()).setIssuedAt(new Date(token.getIat())).setSubject(token.getSub())
				.setIssuer(token.getIss()).setExpiration(expirationDate);

		LOGGER.info("IssuedAt date : " + new Date(token.getIat()));
		LOGGER.info("exp date: " + expirationDate);

		JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm, signingKey);

		builder.setHeaderParam(JwsHeader.ALGORITHM, "HS256").setHeaderParam(JwsHeader.TYPE, "JWT");

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}	

	/**
	 * Parse JWT
	 * 
	 * @param token
	 * @param secretKey
	 * @param signatureKey
	 */
	public static void parseJWT(String token, SecretKey secretKey, String signatureKey) {

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(signatureKey))
				.parseClaimsJws(token).getBody();

		if (claims != null) {
			String payloadEcripted = (String) claims.get(PAYLOAD);
			String payloadPlain = decrypt(ALGORITHM_EAS, payloadEcripted, secretKey);
			JWTPayload p;
			JWToken tokenBean = new JWToken();
			try {
				p = new ObjectMapper().readValue(payloadPlain, JWTPayload.class);

				tokenBean.setIss(claims.getIssuer());
				tokenBean.setPayload(p);
				tokenBean.setIat(claims.getIssuedAt().getTime());
				long expirationTime = claims.getExpiration() == null? 0: claims.getExpiration().getTime();
				tokenBean.setExp(expirationTime);

				LOGGER.info("============== After Parse data  =========================== ");
				LOGGER.info("userName : " + tokenBean.getPayload().getUserName());
				LOGGER.info("password : " + tokenBean.getPayload().getPassword());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("ID: " + claims.getId());
		LOGGER.info("Subject: " + claims.getSubject());
		LOGGER.info("Issuer: " + claims.getIssuer());
		LOGGER.info("IssuedAt: " + claims.getIssuedAt());
		LOGGER.info("Expiration: " + claims.getExpiration());
		LOGGER.info("claims : " + claims);

	}

	/**
	 * Decript JWT
	 * 
	 * @param algorithmAES
	 * @param strToDecrypt
	 * @param secretKey
	 * @return
	 */
	public static String decrypt(String algorithmAES, String strToDecrypt, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(algorithmAES);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt.getBytes())));
			return decryptedString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check JWT is experied
	 * 
	 * @param claims
	 * @return
	 */
	public static boolean isExpired(Claims claims) {
		if (claims == null) {
			return true;
		}
		Date expiresOn = claims.getExpiration();
		if (expiresOn != null) {
			return new Date().getTime() > expiresOn.getTime();
		} else {
			return false;
		}
	}
	
	
	//Sample method to construct a JWT
	public String createJWT(String id, String issuer, String subject, long ttlMillis) {

		SecretKey signatureKey = null;
		try {
			signatureKey = KeyGenerator.getInstance(ALGORITHM).generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	    //The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);

	    //We will sign our JWT with our ApiKey secret
	    //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signatureKey.toString());
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .setIssuer(issuer)
	                                .signWith(signatureAlgorithm, signingKey);

	    //if it has been specified, let's add the expiration
	    if (ttlMillis >= 0) {
	    	long expMillis = nowMillis + ttlMillis;
	        
	    	//Date expirationDate = token.getExp() == null? null: new Date(token.getExp());
	    	
	    	Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }

	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}
	
	//Sample method to validate and read the JWT
	public void parseJWT(String jwt) {
	 
		SecretKey signatureKey = null;
		try {
			signatureKey = KeyGenerator.getInstance(ALGORITHM).generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		
	    //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()         
	       //.setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
	       .setSigningKey(DatatypeConverter.parseBase64Binary(signatureKey.toString()))
	       .parseClaimsJws(jwt).getBody();
	    LOGGER.info("ID: " + claims.getId());
	    LOGGER.info("Subject: " + claims.getSubject());
	    LOGGER.info("Issuer: " + claims.getIssuer());
	    LOGGER.info("Expiration: " + claims.getExpiration());
	}
	
}
