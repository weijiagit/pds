/**
 * 
 */
package com.fykj.kernel._c;

/**
 *
 * @author J
 */
public class JClassException extends RuntimeException {

	public JClassException(String message){
		super(message);
	}
	
	public JClassException(Exception e){
		super(e);
	}
	
	
}
