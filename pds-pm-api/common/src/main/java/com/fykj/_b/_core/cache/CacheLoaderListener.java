package com.fykj._b._core.cache;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class CacheLoaderListener implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		DictionaryCache dictionaryCache = event.getApplicationContext().getBean(DictionaryCache.class);
		SysParamCache sysParamCache = event.getApplicationContext().getBean(SysParamCache.class);
		dictionaryCache.load();
		sysParamCache.load();
	}

}
