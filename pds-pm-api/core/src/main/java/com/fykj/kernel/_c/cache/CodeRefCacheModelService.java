/**
 * 
 */
package com.fykj.kernel._c.cache;

import java.util.List;


/**
 * system resource interface , to used in request response cached, or any other cases. 
 * @author J
 */
public interface CodeRefCacheModelService extends ResourceCacheModelService{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<? extends CodeRefCacheModel> getResourceCacheModels();
	
}
