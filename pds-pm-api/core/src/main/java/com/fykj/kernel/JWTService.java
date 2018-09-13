package com.fykj.kernel;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {

	@Autowired
	private _Cfg cfg;
	
	private String secret(){
		return cfg.getJwt().getSecret();
	}
	
	private long expiration(){
		return cfg.getJwt().getExpiration();
	};
	
	
	
	public String generateToken(Map<String, Object> claims) {
	    return Jwts.builder()
	            .setClaims(claims)
	            .setExpiration(new Date(System.currentTimeMillis() + expiration()))
	            .signWith(SignatureAlgorithm.HS512, secret())
	            .compact();
	}
	
	public Claims getClaimsFromToken(String token) {
	    Claims claims= Jwts.parser()
                .setSigningKey(secret())
                .parseClaimsJws(token)
                .getBody();
	    return claims;
	}
	
	public boolean isSigned(String token){
		return Jwts.parser()
			.setSigningKey(secret())
			.isSigned(token);
	}
	
	
	
	
	
}
