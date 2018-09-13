package com.fykj.kernel._c.cache.redis;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
import com.fykj.kernel._c._i.JAspect;
import com.fykj.kernel._c.cache.MapCacheService;

@Aspect
@Component
@Order(value=JAspect.HIGHEST_NUMBER)
@Conditional({_RedisOffAspect._KaptchaCondition.class})
public class _RedisOffAspect implements JAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(_RedisOffAspect.class);  
	
	
	private static final String SWITCH="cpp.redis.redis-off-proxy";
	
	public static class _KaptchaCondition implements Condition{

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			Environment environment= context.getEnvironment();
			LOGGER.info("redis-off-proxy enable : "+environment.getProperty(SWITCH));
			return environment.getProperty(SWITCH, Boolean.class,false);
		}
		
	}
	
	@Autowired
	private _Cfg cfg;
	
	@Autowired
	private MapCacheService<String,Object> mapCacheService;
	
	@Pointcut("execution(* com.fykj.kernel._c.cache.redis._RedisCacheService.*(..))")
	public void redisOff() {
	}
	
	@Around(value = "redisOff()")
	public Object aroundLog(ProceedingJoinPoint pjd) throws Throwable {
		try{
			return pjd.proceed();
		}catch (Exception e) {
			LOGGER.error("redis is  off , use map instead of ");
			MethodSignature methodSignature= (MethodSignature)pjd.getSignature();
			String methodName=methodSignature.getName();
			Class[] classes=  methodSignature.getParameterTypes();
			Method method=  mapCacheService.getClass().getMethod(methodName, classes);
			return method.invoke(mapCacheService, pjd.getArgs());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
