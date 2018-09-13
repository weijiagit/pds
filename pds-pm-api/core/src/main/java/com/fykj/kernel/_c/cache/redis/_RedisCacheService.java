package com.fykj.kernel._c.cache.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.cache.RedisCacheService;

@Component
public class _RedisCacheService<K,V> implements RedisCacheService<K,V> {
	
	@Autowired(required=false)
	private RedisTemplate<K, V> redisTemplate ;
	
	@Autowired
	private _Cfg cfg;
	
	@Override
	public V expire(K key, V object) {
		return expire(key, object, cfg.getRedis().getExpiredTime(), TimeUnit.SECONDS);
	}
	
	@Override
	public V expire(K key, V object, long time, TimeUnit timeUnit) {
		BoundValueOperations<K, V> bound= redisTemplate.boundValueOps(key);
		bound.set(object);
		bound.expire(time, timeUnit);
		return object;
	}

	@Override
	public V put(K key, V object) {
		BoundValueOperations<K, V> bound= redisTemplate.boundValueOps(key);
		bound.set(object);
		return null;
	}

	@Override
	public V get(K key) {
		BoundValueOperations<K, V> bound= redisTemplate.boundValueOps(key);
		return bound.get();	
	}

	@Override
	public V remove(K key) {
		redisTemplate.delete(key);
		return null;
	}

	@Override
	public boolean contains(K key) {
		return redisTemplate.hasKey(key);
	}

}
