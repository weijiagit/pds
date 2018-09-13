package com.fykj.pds.jersey.config;

import com.fykj.pds.jersey.server.WebChatInterfaceServer;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.fykj.pds.jersey.server.InterfaceServer;

/*
 * 注册服务
 */
@SuppressWarnings("deprecation")
public class JerseyApplication extends ResourceConfig {

	public JerseyApplication() {
		//加载Resource
		register(InterfaceServer.class) ;
		register(WebChatInterfaceServer.class);
		//注册数据转换器
		register(JacksonJsonProvider.class);
		// Logging.
		register(LoggingFilter.class);

	}
}
