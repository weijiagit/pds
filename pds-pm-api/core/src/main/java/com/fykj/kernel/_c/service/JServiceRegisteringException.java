/**
 * 
 */
package com.fykj.kernel._c.service;

/**
 * that indicates the service cannot be registered, as that already exits.
 * @author J
 */
public class JServiceRegisteringException extends RuntimeException {

	public JServiceRegisteringException(String message){
		super(message);
	}
	
	public JServiceRegisteringException(Exception e){
		super(e);
	}
	
}
