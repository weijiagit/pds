package com.fykj.kernel._c;

import com.fykj.kernel.BusinessException;

/**
 * for any validating exception
 * @author J
 *
 */
public class ValidationException extends BusinessException {

	public ValidationException(String message){
		super(message);
	}
	
	public ValidationException(Exception e){
		super(e);
	}
	
}
