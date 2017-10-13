package com.jwtexample.jwt;

/**
 * @author bhawani.singh
 * 
 * JSON web token
 */
public class JWToken {

	private JWTPayload payload;
	private String jti;
	private Long iat;
	private String sub;
	private String iss;
	private Long exp;
	
	/**
	 * 
	 */
	public JWToken() {
		super();
	}

	/**
	 * @param payload
	 * @param jti
	 * @param iat
	 * @param sub
	 * @param iss
	 * @param exp
	 */
	public JWToken(JWTPayload payload, String jti, Long iat, String sub, String iss, Long exp) {
		super();
		this.payload = payload;
		this.jti = jti;
		this.iat = iat;
		this.sub = sub;
		this.iss = iss;
		this.exp = exp;
	}

	/**
	 * @return the payload
	 */
	public JWTPayload getPayload() {
		return payload;
	}

	/**
	 * @param payload
	 *            the payload to set
	 */
	public void setPayload(JWTPayload payload) {
		this.payload = payload;
	}

	/**
	 * @return the jti
	 */
	public String getJti() {
		return jti;
	}

	/**
	 * @param jti
	 *            the jti to set
	 */
	public void setJti(String jti) {
		this.jti = jti;
	}

	/**
	 * @return the iat
	 */
	public Long getIat() {
		return iat;
	}

	/**
	 * @param iat
	 *            the iat to set
	 */
	public void setIat(Long iat) {
		this.iat = iat;
	}

	/**
	 * @return the sub
	 */
	public String getSub() {
		return sub;
	}

	/**
	 * @param sub
	 *            the sub to set
	 */
	public void setSub(String sub) {
		this.sub = sub;
	}

	/**
	 * @return the iss
	 */
	public String getIss() {
		return iss;
	}

	/**
	 * @param iss
	 *            the iss to set
	 */
	public void setIss(String iss) {
		this.iss = iss;
	}

	/**
	 * @return the exp
	 */
	public Long getExp() {
		return exp;
	}

	/**
	 * @param exp
	 *            the exp to set
	 */
	public void setExp(Long exp) {
		this.exp = exp;
	}

}
