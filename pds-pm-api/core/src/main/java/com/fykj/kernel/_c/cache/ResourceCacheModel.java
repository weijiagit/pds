/**
 * 
 */
package com.fykj.kernel._c.cache;

import com.fykj.kernel._c.model.JModel;


/**
 * ANY resource that need be cached in any form.
 * @author J
 */
public interface ResourceCacheModel extends JModel{

	/**
	 * the resource uri.
	 * @return
	 */
	public String getUri();
	
}
