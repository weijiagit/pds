/**
 * 
 */
package com.fykj.kernel._c.cache;




/**
 * system resource interface.
 * @author J
 */
public interface ResourceCacheService<T> extends InitialResource,JCacheService<String,T>{
	
	IdentifierGenerator generator();
	
}
