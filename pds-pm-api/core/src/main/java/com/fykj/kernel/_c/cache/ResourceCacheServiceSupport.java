package com.fykj.kernel._c.cache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fykj.kernel._c.service.JServiceLazyProxy;
import com.fykj.kernel._c.service.ServiceSupport;

public class ResourceCacheServiceSupport<T,M> extends ServiceSupport implements ResourceCacheService<T>{

	private JCacheService<String,T> cacheService=JServiceLazyProxy.proxy(EhCacheService.class);
	
	private static DefaultIdentifierGenerator defaultIdentifierGenerator=new DefaultIdentifierGenerator();
	
	private static Set<InitialResource> initialResources=new HashSet<InitialResource>();
	
	public static Set<InitialResource> getInitialResources() {
		return initialResources;
	}
	
	public ResourceCacheServiceSupport() {
		initialResources.add(this);
	}
	
	@Override
	public IdentifierGenerator generator() {
		return defaultIdentifierGenerator;
	}

	@Override
	public T get(String key) {
		return (T) cacheService.get(generator().key(key));
	}

	public T remove(String key) {
		return cacheService.remove(generator().key(key));
	}
	
	@Override
	public T put(String key, T object) {
		return cacheService.put(key, object);
	}
	
	@Override
	public T expire(String key, T object) {
		return cacheService.expire(key, object);
	}
	
	
	@Override
	public T expire(String key, T object, long time, TimeUnit timeUnit) {
		return cacheService.expire(key, object, time, timeUnit);
	}
	
	@Override
	public boolean contains(String key) {
		return cacheService.contains(generator().key(key));
	}

	@Override
	public void initResource() {
		
	}

}
