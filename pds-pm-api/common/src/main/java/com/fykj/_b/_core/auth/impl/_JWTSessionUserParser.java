package com.fykj._b._core.auth.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fykj.kernel.JWTService;
import com.fykj.kernel.SessionUserParser.JWTSessionUserParser;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.util.JStringUtils;

import io.jsonwebtoken.Claims;

@Component
public class _JWTSessionUserParser implements JWTSessionUserParser {

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private JWTClaims jwtClaims;
	
	@Override
	public SessionUser parse(String source) {
		if(JStringUtils.isNotNullOrEmpty(source)){
			Claims claims=jwtService.getClaimsFromToken(source);
			SessionUser sessionUser=jwtClaims.sessionUser(claims);
			return sessionUser;
		}
		return null;
	}

}
