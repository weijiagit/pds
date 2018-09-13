package com.fykj.kernel;

/**
 * for any case the developers themselves throw expected exception, such as 
 * business domain error. 
 * @author JIAZJ
 *
 */
public class BusinessException extends RuntimeException {

	public BusinessException(String message){
		super(message);
	}
	
	public BusinessException(Exception e){
		super(e);
	}
	
}
