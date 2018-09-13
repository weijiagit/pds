package com.fykj.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;


/**
 * Created by J on 2016/3/9.
 */
public class Copy {
	
	public static String[] ignoreProperties = new String[]{"id","version","isAvailable","creatorId", "createDate", "modifierId", "modifyDate"};

	/**
	 * @param dto
	 * @param targetEntity
	 * @return
	 */
	public static <T> T simpleCopy(Object dto,Class<T> targetEntity){
		try {
			T object=targetEntity.newInstance();
			BeanUtils.copyProperties(dto, object);
			return object;
		} catch (BeansException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T simpleCopy(Object dto,Class<T> targetEntity, String... ignoreProperties) {
		try {
			T object=targetEntity.newInstance();
			BeanUtils.copyProperties(dto, object,ignoreProperties);
			return object;
		} catch (BeansException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void simpleCopyExcludeNull(Object source, Object target)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null &&
							ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							if(value != null){
								writeMethod.invoke(target, value);
							}
						}
						catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param source
	 * @param target
	 * @param ignoreProperties 需要忽略的属性
	 * @throws BeansException
	 */
	public static void simpleCopyIgnoreProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
		BeanUtils.copyProperties(source, target, ignoreProperties);
	}
	
	public static void copyProperties(Object dest, Object orig) {
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch(Exception e) {
			throw new RuntimeException("copy properties exception:" + e);
		}
	}
}
