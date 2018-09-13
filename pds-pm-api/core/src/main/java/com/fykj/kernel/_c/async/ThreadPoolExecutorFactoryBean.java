package com.fykj.kernel._c.async;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolExecutorFactoryBean implements FactoryBean<ThreadPoolExecutor>{

	@Autowired
	private ThreadConfig config;
	
	@Override
	public ThreadPoolExecutor getObject() throws Exception {
		ThreadPoolExecutor executor =new ThreadPoolExecutor
				(config.getAliveCount(), config.getMaxCount(), config.getAliveTime(), TimeUnit.SECONDS, 
						new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
							int index=0;
							@Override
							public Thread newThread(Runnable r) {
								return new Thread(r,"thread-("+(config.getName())+")-"+(++index));
							}
						},new ThreadPoolExecutor.CallerRunsPolicy());
		
		return executor;
	}

	@Override
	public Class<?> getObjectType() {
		return ThreadPoolExecutor.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
