package com.fykj.kernel.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.cache.RedisCacheService;

@Component
public class _redisUidRepository implements UidRepository<String,String> {
	
	private final String BEGIN ="manage_sso_user_id_";
	@Autowired(required=false)
	private RedisCacheService<String, String> redisCacheService;
	
	@Autowired
	private _Cfg cfg;

	@Override
	public void store(String key, String value) throws Exception {
		redisCacheService.expire(BEGIN+key, value,cfg.getRedis().getSessionUserExpiredTime(),TimeUnit.HOURS);
		
	}

	@Override
	public boolean exists(String key, String value) throws Exception {
		String val=redisCacheService.get(BEGIN+key);
		if(val.equalsIgnoreCase(value)){
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(String key) throws Exception {
		redisCacheService.remove(BEGIN+key);
		return true;
	}

	@Override
	public String get(String key) throws Exception {
		return	redisCacheService.get(BEGIN+key);
	}

	@Override
	public boolean contains(String key) throws Exception {
		return redisCacheService.contains(BEGIN+key);
	}
}

