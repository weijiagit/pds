package com.fykj.kernel._c.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class JDefaultFieldMeta implements JFieldMeta{

	private String fieldName;
	
	/**
	 * the scanning class from the field is hit ,  not declared class of the field
	 */
	private Class<?> clazz;
	
	private Annotation[] annotations;
	
	private int access;
	
	private Field field;
	
	private String getterMethodName;
	
	private String setterMethodName;

	private Type[] actualTypeArgument;
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * the scanning class from the field is hit ,  not declared class of the field
	 * @return
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the scanning class from the field is hit ,  not declared class of the field
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Annotation[] annotations) {
		this.annotations = annotations;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getGetterMethodName() {
		return getterMethodName;
	}

	public void setGetterMethodName(String getterMethodName) {
		this.getterMethodName = getterMethodName;
	}

	public String getSetterMethodName() {
		return setterMethodName;
	}

	public void setSetterMethodName(String setterMethodName) {
		this.setterMethodName = setterMethodName;
	}
	
	 /**
     * Returns a {@code Class} object that identifies the
     * declared type for the field represented by this
     * {@code Field} object.
     *
     * @return a {@code Class} object identifying the declared
     * type of the field represented by this object
     */
	public Class<?> getType(){
		return field.getType();
	}
	
	public void setActualTypeArgument(Type[] actualTypeArgument) {
		this.actualTypeArgument = actualTypeArgument;
	}
	
	/**
	 *  if the field type is generic , we can inspect the actual type argument
	 * @return
	 */
	public Type[] getActualTypeArgument() {
		return actualTypeArgument;
	}
	
}
