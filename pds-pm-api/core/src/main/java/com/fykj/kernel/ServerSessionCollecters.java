package com.fykj.kernel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel._c.ServerSessionCollecter;
import com.fykj.kernel.redis.RedisCollecter;

@Component
public class ServerSessionCollecters implements ServerSessionCollecter<HttpServletRequest,HttpServletResponse> {

	@Autowired
	private JWTCollecter jwtCollecter;
	
	@Autowired
	private RedisCollecter RedisCollecter;
	@Override
	public void collect(HttpServletRequest source,HttpServletResponse out) throws JWTTokenException {
	
		
		jwtCollecter.collect(source,out);  // collect JWT
		RedisCollecter.collect(source, out); //collect Redis
	}

	
	
	
}
