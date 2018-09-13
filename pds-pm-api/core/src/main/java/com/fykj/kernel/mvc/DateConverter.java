package com.fykj.kernel.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import com.fykj.util.JStringUtils;


public class DateConverter implements Converter<String, Date> {
	
	@Override    
	public Date convert(String source) {
		if(JStringUtils.isNullOrEmpty(source)) return null;
		//TODO  do more for more date format
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");    
	    dateFormat.setLenient(false);    
	    try {    
	        return dateFormat.parse(source);    
	    } catch (ParseException e) {    
	    	throw new RuntimeException("format error : "+source+" F:yyyy-MM-dd");
	    }           
	}  
	
	
}
