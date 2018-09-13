package com.fykj.kernel._c.cache.ehcache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.cache.EhCacheService;

@Component
public class _EhCacheService implements EhCacheService<String,Object> {
	
	private Cache ehcache;
	
	@Autowired
	private _Cfg cfg;
	
	@Autowired
	public void setEhcache(EhCacheCacheManager ehCacheCacheManager) {
		this.ehcache = ehCacheCacheManager.getCache(cfg.getEhcache().getName());
	}
	
	@Override
	public Object expire(String key, Object object) {
		return expire(key, object, -1, TimeUnit.SECONDS);
	}
	
	@Override
	public Object expire(String key, Object object, long time, TimeUnit timeUnit) {
		return put(key, object);
	}

	@Override
	public Object put(String key, Object object) {
		ehcache.put(key, object);
		return null;
	}

	@Override
	public Object get(String key) {
		ValueWrapper valueWrapper=ehcache.get(key);
		return valueWrapper==null?null: valueWrapper.get();
	}

	@Override
	public Object remove(String key) {
		ehcache.evict(key);
		return null;
	}

	@Override
	public boolean contains(String key) {
		return ehcache.get(key)!=null;
	}

}
