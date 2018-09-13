/**
 * 
 */
package com.fykj.util.property;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhengzw
 * property 文件
 */
public class PropertyLoader {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);
	
	/**
	 * 读取classpath 下配置文件
	 * @param name 配置文件名称
	 * 还需要修改修改
	 * @return
	 */
	public static Properties loadProperties(String name) {
		
		Properties prop = new Properties();
		
		try {
			if(prop.isEmpty()) {
				synchronized (PropertyLoader.class) {
					
					if(prop.isEmpty()){
						InputStream inStream = getPropertyPath(name);
						prop.load(inStream);

						inStream.close();
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new RuntimeException(e);
		}
		
		return prop;
	}
	
	private static InputStream getPropertyPath(String name) {
		InputStream is = null;
		
		is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		
		return is;
	}
}
