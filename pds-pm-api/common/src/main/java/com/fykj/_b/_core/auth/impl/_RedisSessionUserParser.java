package com.fykj._b._core.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel.SessionUserException;
import com.fykj.kernel.SessionUserParser.RedisSessionUserParser;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.kernel.redis.UserRepository;
import com.fykj.util.JStringUtils;
import com.fykj.util.http.HttpClientComponent;

@Component
public class _RedisSessionUserParser implements RedisSessionUserParser {
	@Autowired
	private UserRepository<String, SessionUser> userRepository;
	
	@Override
	public SessionUser parse(String source) {
		if(JStringUtils.isNotNullOrEmpty(source)){
			if(userRepository.contains(source)){
				try {
					return userRepository.get(source);
				} catch (Exception e) {
					throw new SessionUserException(e);
				}
			}
		}
		return null;
	}

}
