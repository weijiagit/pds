package com.fykj.kernel;

import com.fykj.kernel._c.model.SessionUser;

public interface SessionUserParser<T> {

	SessionUser parse(T source);
	
	public static interface JWTSessionUserParser extends SessionUserParser<String>{
		
	}
	public static interface RedisSessionUserParser extends SessionUserParser<String>{
		
	}

}
