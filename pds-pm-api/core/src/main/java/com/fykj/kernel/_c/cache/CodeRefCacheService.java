/**
 * 
 */
package com.fykj.kernel._c.cache;




/**
 * cache all code-names.
 * @author J
 */
public interface CodeRefCacheService<T> extends ResourceCacheService<T>{
	
	public String getName(String type,String code);
	
}
