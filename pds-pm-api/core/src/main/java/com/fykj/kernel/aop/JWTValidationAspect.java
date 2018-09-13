package com.fykj.kernel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fykj.kernel.JWTService;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c._i.JAspect;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.util.JStringUtils;

@Aspect
@Component
@Order(value=JWTValidationAspect.ORDER_NUMBER)
public class JWTValidationAspect implements JAspect {

	public static final int ORDER_NUMBER=ExceptionAspect.ORDER_NUMBER+100;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JWTValidationAspect.class);  
	
	@Autowired
	private JWTService jwtService;
	
	
	@Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))"
//			+ "&& !@annotation(com.fykj.kernel.aop.NoClosureException)"
			)
	public void jwtValidation() {
	}
	
	@Around(value = "jwtValidation()")
	public Object aroundLog(ProceedingJoinPoint pjd) throws Throwable {
		String jwt=ServerSessionHolder.getServerSession().getJwt();
		if(JStringUtils.isNotNullOrEmpty(jwt)){
			if(!jwtService.isSigned(jwt)){
				return InvokeResult.bys("E001");//Invalid-JWT
			}
			try{
				//attempt to check if the JWT is valid.
				jwtService.getClaimsFromToken(jwt);
			}catch (Exception e) {
				return InvokeResult.bys("E001");//Invalid-JWT
			}
		}
		return pjd.proceed();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
