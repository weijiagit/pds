package com.fykj.kernel._c._d_mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fykj.kernel._c.model.JInputModel;
import com.fykj.web.model.SimplePageRequestVO;

/**
 * @author J
 *
 * @param <T>
 */
@Deprecated
public class ControllerSupport implements ApplicationContextAware ,InitializingBean,BeanNameAware  {
	
	private String beanName;
	
	@Override
	public void setBeanName(String name) {
		this.beanName=name;
	}
	
	public String getBeanName() {
		return beanName;
	}
	
	protected final Logger LOGGER= LoggerFactory.getLogger(getClass());
	
	protected ApplicationContext applicationContext=null;
	
	protected ControllerSupport(){}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	public void afterPropertiesSet() throws Exception {
		Class<?> classIncudeMethod=this.getClass();
		Method[] methods=classIncudeMethod.getMethods();
		if(methods.length>0){
			for (int j = 0; j < methods.length; j++) {
				Method method=methods[j];
				RequestMapping methodRequestMapping= method.getAnnotation(RequestMapping.class);
				
				if(methodRequestMapping!=null){ // has controller mapping.
					MappingMeta resourceInfo=new MappingMeta();
					resourceInfo.setClazz(classIncudeMethod);
					
					Controller controller=classIncudeMethod.getAnnotation(Controller.class);
					if(controller!=null){
						resourceInfo.setControllerName(controller.value());
					}
					else{
						throw new IllegalStateException(" class not represented by "+Controller.class);
					}
					resourceInfo.setMethodName(method.getName());
					RequestMapping classRequestMapping= classIncudeMethod.getAnnotation(RequestMapping.class);
					
					String[] methodPaths= methodRequestMapping.value();
					String path="";
					if(classRequestMapping!=null){
						String[] classPaths=classRequestMapping.value();
						if(classPaths.length>0){
							path=classPaths[0];
						}
					}
					if(methodPaths.length>0){
						path=path+methodPaths[0];
					}
					resourceInfo.setPath(path);
					Class<?>[] parameters= method.getParameterTypes();
					MethodParamMeta[] methodParamMetas=new MethodParamMeta[parameters.length];
					for(int i=0;i<parameters.length;i++){
						Class<?> parameter=parameters[i];
						MethodParamMeta paramMeta=new MethodParamMeta();
						paramMeta.setType(parameter);
						paramMeta.setName("not got any parameter name!");
						paramMeta.setAnnotations(parameter.getAnnotations());
						methodParamMetas[i]=paramMeta;
					}
					resourceInfo.setMethodAnnotations(method.getAnnotations());
					resourceInfo.setMethodParams(methodParamMetas);
					//validate(resourceInfo);
					MappingHub.putMappingMeta(resourceInfo.getPath(), resourceInfo);
					MappingHub.putControllerObject(resourceInfo.getPath(), this);
				}
			}
		}
	}
	
	private void validate(MappingMeta mappingMeta){
		
		Annotation[] annotations=mappingMeta.getMethodAnnotations();
		if(annotations.length>0){
			for(Annotation annotation:annotations){
				if(SkipMappingCheck.class==annotation.annotationType()){
					return ;
				}
			}
		}
		
		
		 Class<?>[] methodParamClasses = mappingMeta.getMethodParamClasses();
         Object[] parameters = new Object[methodParamClasses.length];
         boolean validate=true;
         if(validate){
         	String errorMessage="the method argument is invlaid :"+mappingMeta.getPath();
             if(methodParamClasses.length==0){
             	throw new RuntimeException(errorMessage);
             }
             if(methodParamClasses.length>0){
//             	if(ServiceContext.class!=methodParamClasses[0]){
//             		throw new RuntimeException(errorMessage);
//             	}
             	
             	if(parameters.length==2){
             		//do validation.
             		Class<?> clazz=methodParamClasses[1];
             		if(!(SimplePageRequestVO.class==clazz
             				||isAcceptedSimpleClass(clazz)
             				||isCompositeClass(clazz)
             				||JInputModel.class.isAssignableFrom(clazz))){
             			throw new RuntimeException(errorMessage);
             		}
             	}
             	
             	if(parameters.length==3){
             		//do validation.
             		Class<?> clazz=methodParamClasses[1];
             		if(!(JInputModel.class.isAssignableFrom(clazz)
             				||isCompositeClass(clazz)
             				||isAcceptedSimpleClass(clazz))){
             			throw new RuntimeException(errorMessage);
             		}
             		
             		clazz=methodParamClasses[2];
             		if(SimplePageRequestVO.class!=clazz){
             			throw new RuntimeException(errorMessage);
             		}
             	}
             	
             }
         }
	}
	
	public static boolean isCompositeClass(Class<?> clazz){
		if(clazz.isArray()
				||List.class.isAssignableFrom(clazz)
				||Map.class.isAssignableFrom(clazz)){
			return true;
		}
		return false;
	}
	
	public static boolean isAcceptedSimpleClass(Class<?> clazz){
		if(String.class==clazz
				||Byte.class==clazz
				||Short.class==clazz
				||Integer.class==clazz
				||Long.class==clazz
				||Float.class==clazz
				||Double.class==clazz
				||BigDecimal.class==clazz
				||byte.class==clazz
				||short.class==clazz
				||int.class==clazz
				||long.class==clazz
				||float.class==clazz
				||double.class==clazz
				){
			return true;
		}
		return false;
	}
	
	
	public Object getBeanObject(){
		return applicationContext.getBean(beanName);
	}
	
}
