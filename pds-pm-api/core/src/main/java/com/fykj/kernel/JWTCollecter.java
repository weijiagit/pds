package com.fykj.kernel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel._c.ServerSession;
import com.fykj.kernel._c.ServerSessionCollecter;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.util.JStringUtils;

@Component
public class JWTCollecter implements ServerSessionCollecter<HttpServletRequest,HttpServletResponse> {

	@Autowired
	private _Cfg cfg;
	
	@Autowired
	private JWTService jwtService;
	
	@Override
	public void collect(HttpServletRequest source,HttpServletResponse out) {
		String tokenKey=cfg.getJwt().getHeader();
		String jwt=source.getHeader(tokenKey);
		if(JStringUtils.isNotNullOrEmpty(jwt)){
			if(!jwtService.isSigned(jwt)){
				throw new JWTTokenException("Invalid JWT");
			}
		}
		ServerSession serverSession=ServerSessionHolder.getServerSession();
		serverSession.setJwt(jwt);
	}

}
