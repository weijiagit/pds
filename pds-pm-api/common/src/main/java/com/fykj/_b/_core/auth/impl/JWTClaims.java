package com.fykj._b._core.auth.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fykj.kernel._c.model.SessionUser;

/**
 * 		claims.put("un", sessionUser.getUserName());<br/>
		claims.put("nn", sessionUser.getNatureName());<br/>
		claims.put("i", sessionUser.getId());<br/>
		claims.put("p", sessionUser.getPassword());<br/>
 * @author JIAZJ
 *
 */
@Component
public class JWTClaims {

	public Map<String, Object> claims(SessionUser sessionUser,Map<String, Object> ext){
		Map<String, Object> claims=new HashMap<>();
		if(ext!=null){
			claims.putAll(ext);
		}
		claims.put("un", sessionUser.getUserName());
		claims.put("nn", sessionUser.getNatureName());
		claims.put("i", sessionUser.getId());
		claims.put("p", sessionUser.getPassword());
		return claims;
	}

	public SessionUser sessionUser(Map<?,?> claims){
		String userName=(String) claims.get("un");
		String natureName=(String) claims.get("nn");
		String id=(String) claims.get("i");
		String password=(String) claims.get("p");
		
		SessionUser sessionUser=new SessionUser();
		sessionUser.setId(id);
		sessionUser.setUserName(userName);
		sessionUser.setNatureName(natureName);
		sessionUser.setPassword(password);
		return sessionUser;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
