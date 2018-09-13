package com.fykj.kernel._c.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JServiceLazyProxy implements InvocationHandler {
	
	public static final Logger logger = LoggerFactory.getLogger(JServiceLazyProxy.class);
	
	private Logger LOGGER;

	private Object target;

	private Class<?> serviceClass;

	private static  final JServiceHub SERVICE_HUB=JServiceHub.getInstance();


	@SuppressWarnings("unchecked")
	public static <T> T proxy(Class<T> clazz){
		
		logger.debug("initialize proxy for , interface : "+clazz.getName());
		try{
			T proxyT=null;
			JServiceLazyProxy proxy=new JServiceLazyProxy();
			proxy.serviceClass=clazz;
			if(clazz.isInterface()){
				proxyT= (T)java.lang.reflect.Proxy.newProxyInstance
						(clazz.getClassLoader(), new Class[]{ clazz }, proxy);
			}
			return proxyT;
			
		}catch (Exception e) {
			logger.error("initialized proxy fail : ",e);
			throw new RuntimeException(e);
		}
	}
	
	private final Lock lock=new ReentrantLock();
	
	private void logging(String message){
		if(LOGGER!=null){
			LOGGER.debug(message);
		}
		else{
			System.out.println(message);
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try{
			logging("--------------------------------invoke method (JDK) : ("+method.getName()+")...........................");
			if(target==null){
				logging("--------------target is null..........................");
				try{
					lock.lock();
					if(target==null){
						logging(" getting service from hub (JDK) : "+serviceClass.getName());
						LOGGER=LoggerFactory.getLogger(serviceClass.getName());
						target=SERVICE_HUB.getService(serviceClass);
						logging(" had get service from hub (JDK) : "+String.valueOf(target));
					}
				}finally{
					lock.unlock();
				}
			}

//			if(logger.isDebugEnabled()){
//				if(target!=null){
//					return method.invoke(target, args);
//				}
//				return null;
//			}
//			else{
//				return method.invoke(target, args);
//			}
			return method.invoke(target, args);
		}catch (Exception e) {
			LOGGER.error("invoke proxy exception : ", e);
			if(e.getCause()!=null){
				throw e.getCause();
			}
			else{
				throw e;
			}
		}
	}

}
