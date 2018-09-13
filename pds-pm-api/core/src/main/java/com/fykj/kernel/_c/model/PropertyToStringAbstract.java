package com.fykj.kernel._c.model;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyToStringAbstract{
	
	private final Logger LOGGER= LoggerFactory.getLogger(getClass());
	
	@Override
	public String toString() {
		try {
			Class<?> clazz = getClass();
			StringBuffer buffer = new StringBuffer("{");
			superClassFields(clazz, buffer);
			buffer.append("}");
			return super.toString()+"->"+buffer.toString();
		} catch (Exception e) {
			LOGGER.error("对象json转化出错输出默认toString() ["+super.toString()+"]",e);
			return super.toString();
		}
	}
	
	private void superClassFields(Class<?> clazz,StringBuffer buffer) throws IllegalArgumentException, IllegalAccessException{
		Class<?> superClazz = clazz.getSuperclass();
		if(!superClazz.equals(Object.class)){
			superClassFields(superClazz, buffer);
		}
		classFields(clazz, buffer);
	}
	
	private void classFields(Class<?> clazz,StringBuffer buffer) throws IllegalArgumentException, IllegalAccessException{
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				if((field.getType() == Logger.class)){
					continue;
				}
				field.setAccessible(true);
				buffer.append(field.getName()+":"+field.get(this)+",");
			}
		}
	}
}
