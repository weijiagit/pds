/**
 * 
 */
package com.fykj._b._core.jwt;

import com.fykj._b._core.auth.impl.JWTClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fykj.kernel.JWTService;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.util.JStringUtils;

import io.jsonwebtoken.Claims;

/**
 * JWT Utilities Endpoint
 * @author JIAZJ
 *
 */
@Controller
@RequestMapping("/jwt")
public class JWTController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private JWTClaims jwtClaims;
	
	
	/**
	 * refresh the JWT, so the caller can continue accessing all authorized resources 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path="/refresh",method=RequestMethod.GET)
	public InvokeResult refresh(String jwt) throws Exception {
		if(JStringUtils.isNotNullOrEmpty(jwt)){
			if(!jwtService.isSigned(jwt)){
				return InvokeResult.bys("E001");//Invalid-JWT
			}
			Claims claims=null;
			try{
				//attempt to check if the JWT is valid.
				claims=jwtService.getClaimsFromToken(jwt);
			}catch (Exception e) {
				return InvokeResult.bys("E001");//Invalid-JWT
			}
			
			String _jwt=jwtService.generateToken(claims);
			
			return InvokeResult.success(_jwt);
		}else{
			return InvokeResult.bys("E001");//Invalid-JWT
		}
	}
	
	
	/**
	 * validate whether the JWT is valid or not,  only either true or false can be replied
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(path="/validate",method=RequestMethod.GET)
	public InvokeResult validate(String jwt) throws Exception {
		if(JStringUtils.isNotNullOrEmpty(jwt)){
			if(!jwtService.isSigned(jwt)){
				return InvokeResult.success(false);
			}
			try{
				//attempt to check if the JWT is valid.
				jwtService.getClaimsFromToken(jwt);
			}catch (Exception e) {
				return InvokeResult.success(false);
			}
			return InvokeResult.success(true);
		}else{
			return InvokeResult.success(false);
		}
	}
	
}
