package com.fykj.pds.jersey.config;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JerseyConfig {

	@Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/jk/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyApplication.class.getName());
        return registration;
    }
}
