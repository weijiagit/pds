package com.fykj.kernel._c.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fykj.util.JUniqueUtils;

/**
 * Support all services registered in the spring container. 
 * <p><strong>override the method {@link #getService()} to expose the self.</strong> 
 * @author J
 */
public  class SpringServiceFactorySupport implements ApplicationContextAware 
	,InitializingBean,BeanNameAware {

	protected final Logger LOGGER= LoggerFactory.getLogger(getClass());
	
	protected ApplicationContext applicationContext=null;

	protected String beanName;
	
	
	/**
	 * the class registered in service hub.
	 */
	private final Class registClass;


	private static  final JServiceHub SERVICE_HUB=JServiceHub.getInstance();
	
	public SpringServiceFactorySupport(){
		if(this.getClass().getName().contains("$$EnhancerByCGLIB$$")){
			registClass=null;
		}
		else{
			Class<?>[] types= this.getClass().getInterfaces();
			if(types.length==0){
				this.registClass=null;
			}
			else{
				this.registClass=types[0];
			}
		}
	}
	
	/**
	 * register a class 
	 * @param registClass
	 */
	public SpringServiceFactorySupport(Class registClass){
		this.registClass=registClass;
	}
	

	protected final <M> M getBeanByName(String beanName,Class<M> clazz){
		return (M) applicationContext.getBean(beanName,clazz);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(isCanRegister()){
			SERVICE_HUB.register(registClass, getService());
			LOGGER.info("registering service :["+registClass.getName()+"] powered by "+this.getClass().getName());
		}
		else{
			LOGGER.info("No registering service , ignore this service : [ *** ] powered by "+this.getClass().getName());
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	@Override
	public void setBeanName(String name) {
		this.beanName=name;
	}

	/**
	 * override the method by the sub-class. 
	 * @return
	 */
	public SpringServiceFactorySupport getService() {
		return this;
	}


	public String getName() {
		return this.getClass().getName();
	}

	public String getUniqueId() {
		return JUniqueUtils.unique();
	}

	public String describer() {
		return getName()+","+getUniqueId();
	}

	protected boolean isCanRegister(){
		return true;
	}
	
	public String getBeanName() {
		return beanName;
	}

}
