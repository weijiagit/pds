package com.fykj.kernel._c.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.hibernate.mapping.Map;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JOutput {
	
	/**
	 * the output class
	 * @return
	 */
	Class<?> clazz() default Map.class;

}
