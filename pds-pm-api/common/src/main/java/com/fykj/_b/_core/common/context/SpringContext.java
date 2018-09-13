package com.fykj._b._core.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext context = null;
	private static SpringContext stools = null;

	public synchronized static SpringContext init() {
		if (stools == null) {
			stools = new SpringContext();
		}
		return stools;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public synchronized static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public synchronized static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}

}
