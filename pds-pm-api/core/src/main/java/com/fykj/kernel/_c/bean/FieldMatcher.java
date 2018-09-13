package com.fykj.kernel._c.bean;

import java.lang.reflect.Field;

public interface FieldMatcher {
	
	/**
	 * accept the field of the field matches matcher 
	 * @param field
	 * @param scanClass differ from the declared class
	 * @return
	 */
	boolean matches(Field field,Class<?> scanClass);

}
