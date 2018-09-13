package com.fykj.kernel;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="cpp.codes")
public class Codes {
	
	/**
	 * Additional description properties to set on Error Code
	 */
	private Map<String, String> properties = new HashMap<String, String>();
	
	public String get(String code){
		return properties.getOrDefault(code, "");
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	public static interface _CodeNames{
		
		public static final String E0001="E0001";  // for JWT-TOKEN 
		
	}
	
	
}
