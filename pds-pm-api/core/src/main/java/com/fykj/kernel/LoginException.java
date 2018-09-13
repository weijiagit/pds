package com.fykj.kernel;

/**
 * for any case the developers themselves throw expected exception, such as 
 * business domain error. 
 * @author JIAZJ
 *
 */
public class LoginException extends RuntimeException {

	public LoginException(String message){
		super(message);
	}
	
	public LoginException(Exception e){
		super(e);
	}
	
}
