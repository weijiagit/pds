package com.fykj.kernel;

/**
 * for any case the code executing throws unknown issue.
 * @author JIAZJ
 */
public class SystemRuntimeException extends BusinessException {

	public SystemRuntimeException(String message){
		super(message);
	}
	
	public SystemRuntimeException(Exception e){
		super(e);
	}
	
}
