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

import com.fykj.kernel.SessionUserParser.JWTSessionUserParser;
import com.fykj.kernel.SessionUserParser.RedisSessionUserParser;
import com.fykj.kernel._c.ServerSession;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c._i.JAspect;
import com.fykj.kernel._c.model.SessionUser;
import com.fykj.util.JStringUtils;

@Aspect
@Component
@Order(value=SessionUserAspect.ORDER_NUMBER)
public class SessionUserAspect implements JAspect{

	public static final int ORDER_NUMBER=JWTValidationAspect.ORDER_NUMBER+100;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionUserAspect.class);  

	@Autowired
	private JWTSessionUserParser sessionUserParser;
	
	@Autowired
	private RedisSessionUserParser redisSessionUserParser;

	
	@Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))"
//			+ "&& !@annotation(com.fykj.kernel.aop.NoClosureException)"
			)
	public void sessionUser() {
	}
	
	@Around(value = "sessionUser()")
	public Object aroundLog(ProceedingJoinPoint pjd) throws Throwable {
		ServerSession serverSession=ServerSessionHolder.getServerSession();
		String jwt=serverSession.getJwt();
		if(JStringUtils.isNotNullOrEmpty(jwt)){
			SessionUser sessionUser=sessionUserParser.parse(jwt);
			serverSession.setSessionUser(sessionUser);
		}else if(JStringUtils.isNotNullOrEmpty(serverSession.get_token())){
			SessionUser parse = redisSessionUserParser.parse(serverSession.get_token());
			serverSession.setSessionUser(parse);
			
		}
		return pjd.proceed();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
