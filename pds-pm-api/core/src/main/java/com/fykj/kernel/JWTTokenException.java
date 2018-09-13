package com.fykj.kernel;

public class JWTTokenException extends SystemRuntimeException{

	public JWTTokenException(String message){
		super(message);
	}
	
	public JWTTokenException(Exception e){
		super(e);
	}
	
	
}
