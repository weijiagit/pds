package com.fykj.kernel.redis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel.SessionUserException;
import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.ServerSession;
import com.fykj.kernel._c.ServerSessionCollecter;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.util.JStringUtils;

@Component
public class RedisCollecter  implements ServerSessionCollecter<HttpServletRequest,HttpServletResponse> {

	@Autowired
	private _Cfg cfg;
	

	@Autowired
	private UserRepository<String, ?> userRepository;
	@Override
	public void collect(HttpServletRequest source, HttpServletResponse out) {
		String tokenKey=cfg.getRedis().getHeader();
		String rToken=source.getHeader(tokenKey);
		if(JStringUtils.isNotNullOrEmpty(rToken)){
			if(!userRepository.contains(rToken)){
				throw new SessionUserException("登录信息失效，请重新登录！");
			}
		}
		
		ServerSession serverSession=ServerSessionHolder.getServerSession();
		serverSession.set_token(rToken);
		
	}
}

