package com.fykj.kernel;

public class ExceptionUtil {
	
	public static String getMostSpecifiedMessage(Exception e){
		return getRootCause(e).getMessage();
	}
	
	
	public static Throwable getRootCause(Exception e) {
		Throwable rootCause = null;
		Throwable cause = e.getCause();
		while (cause != null && cause != rootCause) {
			if(cause instanceof BusinessException){
				rootCause = cause;
				break;
			}
			cause = cause.getCause();
		}
		return rootCause==null?e:rootCause;
	}
	
}
