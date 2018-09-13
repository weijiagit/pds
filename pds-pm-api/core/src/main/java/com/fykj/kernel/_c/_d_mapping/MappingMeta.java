/**
 * 
 */
package com.fykj.kernel._c._d_mapping;

import java.lang.annotation.Annotation;

import org.springframework.stereotype.Controller;

import com.fykj.kernel._c.model.JModel;

/**
 * @author J
 */
public class MappingMeta implements JModel{
	
	/**
	 * name of {@link Controller}
	 */
	private String controllerName;
	
	/**
	 * method name. 
	 */
	private String methodName;
	
	/**
	 * URL path. consist of name of {@link Controller} and method name,   like as '/login.loginaction/index' .
	 * @see  {@link Controller}
	 */
	private String  path;

	/**
	 * class , restricted by {@link Controller}
	 */
	private Class<?> clazz;
	
	private MethodParamMeta[] methodParams;
	
	private Annotation[] methodAnnotations;

	public Class<?>[] getMethodParamClasses(){
		Class<?>[] clazzs=new Class<?>[methodParams.length];
		for(int i=0;i<methodParams.length;i++){
			clazzs[i]=methodParams[i].getType();
		}
		return clazzs;
	}
	
	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public MethodParamMeta[] getMethodParams() {
		return methodParams;
	}

	public void setMethodParams(MethodParamMeta[] methodParams) {
		this.methodParams = methodParams;
	}

	public Annotation[] getMethodAnnotations() {
		return methodAnnotations;
	}

	public void setMethodAnnotations(Annotation[] methodAnnotations) {
		this.methodAnnotations = methodAnnotations;
	}
	
}
