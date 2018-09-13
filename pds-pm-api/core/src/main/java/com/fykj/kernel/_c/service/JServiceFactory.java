/**
 * 
 */
package com.fykj.kernel._c.service;

/**
 * any service provider need implement the factory to expose self.
 * @author J
 *
 */
public interface JServiceFactory<T> {
	/**
	 * get service object. 
	 * @return
	 */
	T getService();	
	
	/**
	 * get service name.
	 * @return
	 */
	String getName();
	
	/**
	 * get unique service identification .
	 * @return
	 */
	String getUniqueId();
	
	/**
	 * describer 
	 * @return
	 */
	String describer();
	
}
