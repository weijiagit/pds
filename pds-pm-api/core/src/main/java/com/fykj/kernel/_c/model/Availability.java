/**
 * 
 */
package com.fykj.kernel._c.model;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiongp
 *
 */
public enum Availability {
	unavailable,available;
	
	public static Availability get(int code){
		String params = "";
		for (Availability availability : Availability.values()) {
			params += availability.ordinal()+",";
			if(availability.ordinal() == code){
				return availability;
			}
		}
		throw new IllegalArgumentException("参数值应为["+StringUtils.removeEnd(params, ",")+"]中的一个");
	}
}
