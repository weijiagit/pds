package com.fykj.springhook;

import com.fykj.CacheKeyNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fykj.kernel._c.cache.RedisCacheService;

@Component
public class CaseMagListener implements ApplicationListener<ContextRefreshedEvent> {

//	@Autowired
//	private CaseMagService caseMagService;
	
	@Autowired
	private RedisCacheService<String,Long> redisCacheService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		long order=caseMagService.maxOrder();
//		redisCacheService.put(CacheKeyNames.CASE_TOP, order);
	}

}
