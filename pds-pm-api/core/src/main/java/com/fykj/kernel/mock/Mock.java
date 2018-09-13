package com.fykj.kernel.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@_Mock
public @interface Mock {

	/**
	 * expected type of the real response , its a class , or list , map 
	 * @return
	 */
	Class<?> type();

	boolean pageable() default false;
	
	
}
