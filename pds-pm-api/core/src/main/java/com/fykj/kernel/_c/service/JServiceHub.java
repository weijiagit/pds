/**
 * 
 */
package com.fykj.kernel._c.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * service hub. auto-loading all the implementations {@link JServiceFactory}
 * @author J
 */
class JServiceHub {
	
	
	protected final Logger LOGGER= LoggerFactory.getLogger(JServiceHub.class);

	 /**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE : service factory .implements <code>JServiceFactory</code>
	 */
	private static Map<Class<?>, SpringServiceFactorySupport> services=new ConcurrentHashMap<Class<?>, SpringServiceFactorySupport>();


	private static final JServiceHub instance=new JServiceHub();

	public static JServiceHub  getInstance(){
		return instance;
	}

	public synchronized void register(Class<?> clazz,SpringServiceFactorySupport service){
		if(services.containsKey(clazz)){
			SpringServiceFactorySupport exists=services.get(clazz);
			throw new JServiceRegisteringException(clazz.getName()+" is registered , name : "
					+ exists.getName()+" , service factory : "+exists.getClass().getName());
		}
		services.put(clazz, service);
	}


	public Object getService(Class<?> clazz){
		SpringServiceFactorySupport serviceFactory=services.get(clazz);
		if(serviceFactory==null){
			return null;
		}
		else{
			Object object= serviceFactory.getService();
			Object aopObject=null;
			SpringApplicationServiceGetService applicationServiceGetService= (SpringApplicationServiceGetService) services.get(SpringApplicationServiceGetService.class);
			if(SpringServiceFactorySupport.class.isInstance(object)){
				aopObject=applicationServiceGetService.getService(((SpringServiceFactorySupport)object).getBeanName());
			}
			else{
				aopObject=applicationServiceGetService.getService(object.getClass());
			}
			return aopObject;
		}

	}



}
