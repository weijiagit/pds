package com.fykj.kernel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fykj.kernel.BusinessException;
import com.fykj.kernel.SessionUserException;
import com.fykj.kernel._c._i.JAspect;
import com.fykj.kernel._c.model.InvokeResult;

@Aspect
@Component
@Order(value=ExceptionAspect.ORDER_NUMBER)
public class ExceptionAspect implements JAspect{

	public static final int ORDER_NUMBER=JAspect.LOWEST_NUMBER+100;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);  
	
	@Pointcut("execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))"
			+ "&& !@annotation(com.fykj.kernel.aop.NoClosureException)"
			)
	public void exception() {
	}
	
	@Around(value = "exception()")
	public Object aroundLog(ProceedingJoinPoint pjd) throws Throwable {
		try {
			return pjd.proceed();
		}catch(SessionUserException e){
			LOGGER.error(e.getMessage(), e);
			return InvokeResult.LYS(e.getMessage());
		}catch (BusinessException e) {
			LOGGER.error(e.getMessage(), e);
			return InvokeResult.bys(e.getMessage());
		}catch (RuntimeException e) {
			LOGGER.error(e.getMessage(), e);
			return InvokeResult.sys(e.getMessage());
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return InvokeResult.sys(e.getMessage());
		}catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
			return InvokeResult.sys(e.getMessage());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
