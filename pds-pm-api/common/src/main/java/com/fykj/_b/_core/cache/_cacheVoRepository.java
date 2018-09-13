package com.fykj._b._core.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj._b._core.sysuser.vo.UserCacheVo;
import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.cache.RedisCacheService;

@Component
public class _cacheVoRepository implements CacheVoRepository<String,UserCacheVo> {

	private final String BEGIN ="manage_cache_vo";
	@Autowired(required=false)
	private RedisCacheService<String, UserCacheVo> redisCacheService;
	
	@Autowired
	private _Cfg cfg;

	
	@Override
	public void store(String key, UserCacheVo value) throws Exception {
		redisCacheService.expire(BEGIN+key, value,cfg.getRedis().getSessionUserExpiredTime(),TimeUnit.HOURS);
		
	}

	@Override
	public boolean exists(String key, UserCacheVo value) throws Exception {
		UserCacheVo val=redisCacheService.get(BEGIN+key);
		if(val.getUserId().equalsIgnoreCase(value.getUserId())){
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
	public UserCacheVo get(String key) throws Exception {
		return	redisCacheService.get(BEGIN+key);
	}

	@Override
	public boolean contains(String key) throws Exception {
		return redisCacheService.contains(BEGIN+key);
	}
}
