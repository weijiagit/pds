package com.fykj.kernel.redis;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fykj.kernel.SessionUserException;
import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.util.JJSON;
import com.fykj.util.http.HttpClientComponent;
import com.fykj.util.http.model.HttpResult;

@Component
public class SSOService {

	@Autowired
	private _Cfg cfg;
	
	@Autowired
	private  UidRepository<String, String> uidRepository;
	@Autowired
	private UserRepository<String, SessionUser> userRepository; 
	 
	 //从sso 获取用户信息
	 public String getUserNameFromSSO(String ssoToken) throws IOException{
		 HashMap<String, String> headers = new HashMap<>();
		 	headers.put("content-type","JSON");
		String url = 	cfg.getSso().getHost()+cfg.getSso().getValidTokenUrl()+ssoToken;
		 HttpResult doPost = HttpClientComponent.getInstance().doPost(url, headers, 1);
		 	String data = doPost.getData();
		String code =  	JJSON.get().readTree(data).get("code").asText();
		 	if(code.equals("0")){
		 		return	JJSON.get().readTree(data).get("data").get("userName").asText();
		 	}else{
		 		String textValue = JJSON.get().readTree(data).get("message").asText();
		 		throw new SessionUserException(textValue);
		 	}
		 	
		 }



	public String generateToken(SessionUser sessionUser) throws Exception {
		String _token = UUID.randomUUID().toString();
		
		String userName = sessionUser.getUserName();
			//清楚其它客户端登录	
//			redisLogOut(userName);
//			uidRepository.store(userName, _token);
			userRepository.store(_token, sessionUser);
		return _token;
	}
	
	/**
	 * 用户登出
	 * @param userName
	 * @author zhangj
	 * @throws Exception 
	 */
	public void redisLogOut(String token) throws Exception{
//		if(uidRepository.contains(userName)){
//			
//			String object = uidRepository.get(userName);
//				uidRepository.remove(object);
//				userRepository.remove(object);
//		}else{
//			//用户未登录
//		}
		
		userRepository.remove(token);
	}



	public boolean getClaimsFromToken(String token) {
		return userRepository.contains(token);
	}
	 
	 

}
