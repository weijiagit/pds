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
import com.fykj.kernel._c.ServerSessionHolder;
import com.fykj.kernel._c._i.JAspect;
import com.fykj.kernel._c.model.InvokeResult;
import com.fykj.kernel.redis.SSOService;
import com.fykj.util.JStringUtils;

@Aspect
@Component
@Order(value=SsoSignInAspect.ORDER_NUMBER)
@Conditional({SsoSignInAspect._SsoSignInCondition.class})
public class SsoSignInAspect  implements JAspect{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SsoSignInAspect.class);  
	private static final String SWITCH="cpp.sso.enable";
	//尽跟JWT后
	public static final int ORDER_NUMBER=JWTValidationAspect.ORDER_NUMBER+1;
	
	@Autowired
	private SSOService ssoService;
	
	@Autowired
	private _Cfg cfg;
	
	public static class _SsoSignInCondition implements Condition{
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			Environment environment= context.getEnvironment();
			LOGGER.info("_SsoSignInCondition enable : "+environment.getProperty(SWITCH));
			return environment.getProperty(SWITCH, Boolean.class,false);
		}
		
	}
	

	
	@Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))"
			+ "&& !@annotation(com.fykj.kernel.aop.NoAuthorization)"
			)
	public void horization() {
	}
	
	@Around(value = "horization()")
	public Object aroundLog(ProceedingJoinPoint pjd)throws Throwable{
		
			//处理优先权低于JWT-即 JWT存在不做业务处理
			if(JStringUtils.isNotNullOrEmpty(ServerSessionHolder.getServerSession().getJwt())){
				return pjd.proceed();
			}
			
			String token = ServerSessionHolder.getServerSession().get_token();
			if(JStringUtils.isNullOrEmpty(token)){
				return pjd.proceed();
//				String loginUrl=cfg.getSso().getHost()+cfg.getSso().getLoginUrl();
//				return InvokeResult.LYS("DL001",loginUrl);
			}
			if(!ssoService.getClaimsFromToken(token)){
				return InvokeResult.LYS("登录信息已过去，或在其他客户登录，请重新登录！");
			}
			
			return pjd.proceed();
	}
}

