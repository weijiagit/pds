package com.fykj._b._core.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fykj._b._core.sysparam.model.SysParam;
import com.fykj._b._core.sysparam.service.SysParamService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel._c.cache.EhCacheService;

@Component
public class SysParamCache {

	private static final String SYSPARAM_CACHE_NAME = "sysparam_cache";

	@Autowired(required=false)
	private EhCacheService<String,Object> ehCacheService;

	@Autowired
	private SysParamService sysParamService;

	public void load() {
		Map<String, String> sysparam = new HashMap<String, String>();
		List<SysParam> list = sysParamService.loadSysParam();
		if (CollectionUtils.isNotEmpty(list)) {
			for (SysParam param : list) {
				sysparam.put(param.getCode(), param.getValue());
			}
		}
		ehCacheService.put(SYSPARAM_CACHE_NAME, sysparam);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> get() {
		Object cache = ehCacheService.get(SYSPARAM_CACHE_NAME);
		if (cache != null) {
			Map<String, String> sysparam = (Map<String, String>) cache;
			return sysparam;
		}
		return null;
	}

	public String getValue(String code) {
		Map<String, String> sysparam = get();
		if (sysparam == null) {
			return null;
		} else {
			return sysparam.get(code);
		}
	}

}
