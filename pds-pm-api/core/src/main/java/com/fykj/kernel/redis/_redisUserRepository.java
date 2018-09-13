package com.fykj.kernel.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.cache.RedisCacheService;
import com.fykj.kernel._c.model.SessionUser;

@Component
public class _redisUserRepository implements UserRepository<String,SessionUser> {
	
	private final String BEGIN ="manage_sso_user_";
	@Autowired(required=false)
	private RedisCacheService<String, SessionUser> redisCacheService;
	
	@Autowired
	private _Cfg cfg;

	@Override
	public void store(String key, SessionUser value) throws Exception {
		redisCacheService.expire(BEGIN+key, value,cfg.getRedis().getSessionUserExpiredTime(),TimeUnit.HOURS);
		
	}

	@Override
	public boolean exists(String key, SessionUser value) throws Exception {
		SessionUser val=redisCacheService.get(BEGIN+key);
		if(val.getId().equalsIgnoreCase(value.getId())){
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
	public SessionUser get(String key) throws Exception {
		return	redisCacheService.get(BEGIN+key);
	}

	@Override
	public boolean contains(String key)  {
		return redisCacheService.contains(BEGIN+key);
	}
}

