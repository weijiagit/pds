/**
 * 
 */
package com.fykj.util;

import static java.util.Locale.ENGLISH;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fykj.kernel._c.JClassException;

/**
 * utils collection for relfecting class. 
 * @author J
 */
public abstract class JClassUtils {

	/**
	 * check whether the argument is class type of not. 
	 * @param obj
	 * @return
	 */
	public static boolean isClass(Object obj){
		return Class.class.isInstance(obj);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> load(String className,ClassLoader classLoader){
		try {
			if(classLoader==null){
				classLoader=Thread.currentThread().getContextClassLoader();
			}
			return (Class<T>) classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new JClassException(e) ;
		}
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 *  @throws JClassException
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> load(String className){
		try {
			ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
			return (Class<T>) classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new JClassException(e) ;
		}
	}
	
	
	/**
	 * get all fields , includes those fields in the {@link Object}
	 * @param clazz
	 * @param deep
	 * @param modifiers  , to filter the method modify is between 
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz,boolean deep,int... modifiers){
		List<Field> fields=new ArrayList<Field>();
		Class<?> superClass=clazz;
		
		if(modifiers!=null&&modifiers.length==1&&modifiers[0]==Modifier.PUBLIC){
			if(deep){
				Collections.addAll(fields, superClass.getFields());
				return fields;
			}
		}
		
		while(superClass!=null){
			Field[] feds=superClass.getDeclaredFields();
			for(int i=0;i<feds.length;i++){
				Field field=feds[i];
				if(!isAccessable(field)){
					field.setAccessible(true);
				}
				boolean exists=modifiers.length==0;
				if(modifiers!=null&&modifiers.length>0){
					for(int mdf=0;mdf<modifiers.length;mdf++){
						if(field.getModifiers()==modifiers[mdf]){
							exists=true;
							break;
						}
					}
				}
				
				if(exists){
					fields.add(field);
				}
			}
			
			if(deep){ // deep scanning
				superClass=superClass.getSuperclass();
			}
			else{
				superClass=null; // break;
			}
		}
		return fields;
	}
	
	/**
	 *  {@link Modifier} NOT PROTECTED OR PRIVATE.
	 * @param field
	 * @return
	 */
	public static boolean isAccessable(Field field){
		int modify=field.getModifiers();
		return !Modifier.isPrivate(modify)&&!Modifier.isProtected(modify);
	}
	
	/**
	 * 
	 * @param containerClass
	 * @param propertyName
	 * @return
	 * @see JPropertyNotFoundException
	 */
	public static Method findGetterMethod(Class<?> containerClass, String propertyName) {
		Class<?> checkClass = containerClass;
		Method getter = null;

		// check containerClass, and then its super types (if any)
		while ( getter == null && checkClass != null ) {
			if ( checkClass.equals( Object.class ) ) {
				break;
			}

			getter = getGetterOrNull( checkClass, propertyName );
			checkClass = checkClass.getSuperclass();
		}

		// if no getter found yet, check all implemented interfaces
		if ( getter == null ) {
			for ( Class<?> theInterface : containerClass.getInterfaces() ) {
				getter = getGetterOrNull( theInterface, propertyName );
				if ( getter != null ) {
					break;
				}
			}
		}

		if ( getter == null ) {
			throw new RuntimeException(
					String.format(
							Locale.ROOT,
							"Could not locate getter method for property [%s#%s]",
							containerClass.getName(),
							propertyName
					)
			);
		}

		getter.setAccessible( true );
		return getter;
	}
	
	private static Method getGetterOrNull(Class<?> containerClass, String propertyName) {
		for ( Method method : containerClass.getDeclaredMethods() ) {
			// if the method has parameters, skip it
			if ( method.getParameterTypes().length != 0 ) {
				continue;
			}

			// if the method is a "bridge", skip it
			if ( method.isBridge() ) {
				continue;
			}

			final String methodName = method.getName();

			// try "get"
			if ( methodName.startsWith( "get" ) ) {
				final String stemName = methodName.substring( 3 );
				final String decapitalizedStemName = Introspector.decapitalize( stemName );
				if ( stemName.equals( propertyName ) || decapitalizedStemName.equals( propertyName ) ) {
					verifyNoIsVariantExists( containerClass, propertyName, method, stemName );
					return method;
				}

			}

			// if not "get", then try "is"
			if ( methodName.startsWith( "is" ) ) {
				final String stemName = methodName.substring( 2 );
				String decapitalizedStemName = Introspector.decapitalize( stemName );
				if ( stemName.equals( propertyName ) || decapitalizedStemName.equals( propertyName ) ) {
					verifyNoGetVariantExists( containerClass, propertyName, method, stemName );
					return method;
				}
			}
		}

		return null;
	}
	
	private static void verifyNoIsVariantExists(
			Class<?> containerClass,
			String propertyName,
			Method getMethod,
			String stemName) {
		// verify that the Class does not also define a method with the same stem name with 'is'
		try {
			final Method isMethod = containerClass.getDeclaredMethod( "is" + stemName );
			// No such method should throw the caught exception.  So if we get here, there was
			// such a method.
			checkGetAndIsVariants( containerClass, propertyName, getMethod, isMethod );
		}
		catch (NoSuchMethodException ignore) {
		}
	}
	
	private static void checkGetAndIsVariants(
			Class<?> containerClass,
			String propertyName,
			Method getMethod,
			Method isMethod) {
		// Check the return types.  If they are the same, its ok.  If they are different
		// we are in a situation where we could not reasonably know which to use.
		if ( !isMethod.getReturnType().equals( getMethod.getReturnType() ) ) {
			throw new RuntimeException(
					String.format(
							Locale.ROOT,
							"In trying to locate getter for property [%s], Class [%s] defined " +
									"both a `get` [%s] and `is` [%s] variant",
							propertyName,
							containerClass.getName(),
							getMethod.toString(),
							isMethod.toString()
					)
			);
		}
	}
	
	private static void verifyNoGetVariantExists(
			Class<?> containerClass,
			String propertyName,
			Method isMethod,
			String stemName) {
		// verify that the Class does not also define a method with the same stem name with 'is'
		try {
			final Method getMethod = containerClass.getDeclaredMethod( "get" + stemName );
			// No such method should throw the caught exception.  So if we get here, there was
			// such a method.
			checkGetAndIsVariants( containerClass, propertyName, getMethod, isMethod );
		}
		catch (NoSuchMethodException ignore) {
		}
	}
	
	
	public static String getSetterMethodName(Field field){
		return getSetterMethodName(field.getName());
	}
	
	public static String getGetterMethodName(Field field){
		return getGetterMethodName(field.getName(), field.getType()==boolean.class||field.getType()==Boolean.class);
	}
	public static String getSetterMethodName(String property){
		String name=capitalize(property);
		return "set"+name;
	}
	
	public static String getGetterMethodName(String property,boolean isBoolean){
		String name=capitalize(property);
		return isBoolean?("is"+name):("get"+name);
		
	}
	
	private static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }
	
	public static boolean isSimpleType(Class<?> clazz){
		return String.class.isAssignableFrom(clazz)
				||Integer.class.isAssignableFrom(clazz)
				||int.class.isAssignableFrom(clazz)
				||Long.class.isAssignableFrom(clazz)
				||long.class.isAssignableFrom(clazz)
				||Double.class.isAssignableFrom(clazz)
				||double.class.isAssignableFrom(clazz)
				||Float.class.isAssignableFrom(clazz)
				||float.class.isAssignableFrom(clazz)
				||Byte.class.isAssignableFrom(clazz)
				||byte.class.isAssignableFrom(clazz)
				||BigDecimal.class.isAssignableFrom(clazz)
				||boolean.class.isAssignableFrom(clazz)
				||Boolean.class.isAssignableFrom(clazz)
				||Date.class.isAssignableFrom(clazz)
				||Timestamp.class.isAssignableFrom(clazz);
	}
	
	public static boolean isSimpleTypeArray(Class<?> clazz){
		if(!clazz.isArray()) return false;
		return String[].class.isAssignableFrom(clazz)
				||Integer[].class.isAssignableFrom(clazz)
				||int[].class.isAssignableFrom(clazz)
				||Long[].class.isAssignableFrom(clazz)
				||long[].class.isAssignableFrom(clazz)
				||Double[].class.isAssignableFrom(clazz)
				||double[].class.isAssignableFrom(clazz)
				||Float[].class.isAssignableFrom(clazz)
				||float[].class.isAssignableFrom(clazz)
				||Byte[].class.isAssignableFrom(clazz)
				||byte[].class.isAssignableFrom(clazz)
				||BigDecimal[].class.isAssignableFrom(clazz)
				||boolean[].class.isAssignableFrom(clazz)
				||Boolean[].class.isAssignableFrom(clazz)
				||Date[].class.isAssignableFrom(clazz)
				||Timestamp[].class.isAssignableFrom(clazz);
	}
	
	
	/**
	 * set value
	 * @param field
	 * @param value
	 * @param model
	 */
	public static void setOnField(Field field,Object value,Object model) {
		try {
			field.setAccessible(true);
			field.set(model, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
