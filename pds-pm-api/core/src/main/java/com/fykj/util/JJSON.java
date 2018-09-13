package com.fykj.util;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JJSON {

	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	{
		//always default
//		mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
	}
	
	private static final JJSON json=new JJSON();
	
	public static JJSON get(){
		return json;
	}
	
	public String formatObject(Object object){
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, object);
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public <T> T parse(String string, Class<T> t){
		try {
			return mapper.readValue(string, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}  
	
	public Map<String, Object> parse(String string){
		try {
			return mapper.readValue(string, new TypeReference<Map<String, Object>>() {});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * Parse a string to Object . 
	 * @param string
	 * @param typeReference
	 * @return
	 */
	public <T> T parse(String string, TypeReference<T> typeReference){
		try {
			return mapper.readValue(string, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public JsonNode readTree(String content) {
		try {
			return mapper.readTree(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
