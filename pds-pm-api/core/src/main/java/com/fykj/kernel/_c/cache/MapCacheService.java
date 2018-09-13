package com.fykj.kernel._c.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class MapCacheService<K,V> implements JCacheService<K,V> {

	private Map<K, V> repo=new ConcurrentHashMap<>();
	
	@Override
	public V expire(K key, V object, long time, TimeUnit timeUnit) {
		return put(key, object);
	}

	@Override
	public V expire(K key, V object) {
		return expire(key, object, -1, TimeUnit.SECONDS);
	}

	@Override
	public V put(K key, V object) {
		return repo.put(key, object);
	}

	@Override
	public V get(K key) {
		return repo.get(key);
	}

	@Override
	public V remove(K key) {
		return repo.remove(key);
	}

	@Override
	public boolean contains(K key) {
		return repo.containsKey(key);
	}
	
	
}
