package com.fykj.kernel;

public class BusinessExceptionUtil {
	
	public static void throwException(Exception e) throws BusinessException{
		if(BusinessException.class.isInstance(e)) throw (BusinessException)e;
		if(RuntimeException.class.isInstance(e)) throw (RuntimeException)e;
		throw new BusinessException(e);
	}
	
	public static String getMostSpecifiedMessage(Exception e){
		return ExceptionUtil.getMostSpecifiedMessage(e);
	}
	
}
