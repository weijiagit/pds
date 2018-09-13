package com.fykj.kernel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

import com.fykj.kernel._Cfg;
import com.fykj.kernel._c.AuthenticationService;
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c._i.JAspect;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel._c.model.SessionUser;

@Aspect
@Component
@Order(value=AuthenticationAspect.ORDER_NUMBER)
@Conditional({AuthenticationAspect._AuthenticationCondition.class})
public class AuthenticationAspect implements JAspect{

	public static final int ORDER_NUMBER=SessionUserAspect.ORDER_NUMBER+100;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationAspect.class);  
	
	@Autowired
	private AuthenticationService authenticatonService;
	
	private static final String SWITCH="cpp.auth.authentication";
	
	public static class _AuthenticationCondition implements Condition{

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			Environment environment= context.getEnvironment();
			LOGGER.info("authentication enable : "+environment.getProperty(SWITCH));
			return environment.getProperty(SWITCH, Boolean.class,false);
		}
		
	}
	
	@Autowired
	private _Cfg cfg;
	
//	private boolean on(){
//	return cfg.getAuth().isAuthentication();
//}
	
	@Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))"
			+ "&& !@annotation(com.fykj.kernel.aop.NoAuthentication)"
			)
	public void authentication() {
	}

	@Around(value = "authentication()")
	public Object aroundLog(ProceedingJoinPoint pjd) throws Throwable {
		SessionUser sessionUser=ServerSessionHolder.getSessionUser();
		boolean auth=authenticatonService.auth(sessionUser);
		if(auth){
			return pjd.proceed();
		}else{
			return InvokeResult.bys("No Authentication User");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
