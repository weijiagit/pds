/**
 * 
 */
package com.fykj.kernel._c._d_mapping;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author J
 */
public class MappingHub{
	
	private static final Map<String, MappingMeta> CONTROLLERS=new ConcurrentHashMap<String, MappingMeta>();
	
	private static final Map<String, ControllerSupport> CONTROLLER_OBJECTS=new ConcurrentHashMap<String, ControllerSupport>();
	
	public static MappingMeta getControllerMetaByPath(String path){
		return CONTROLLERS.get(path);
	}
	
	public static Object getControllerObjectByPath(String path){
		return CONTROLLER_OBJECTS.get(path).getBeanObject();
	}
	
	public static void putMappingMeta(String path,MappingMeta mappingMeta){
		CONTROLLERS.put(path, mappingMeta);
	}
	
	public static void putControllerObject(String path,Object controllerObject){
		CONTROLLER_OBJECTS.put(path, (ControllerSupport) controllerObject);
	}
	
	public static Collection<String> getAllPaths(){
		return CONTROLLERS.keySet();
	}
	
	public static Collection<MappingMeta> getAllMappingMetas(){
		return Collections.unmodifiableCollection(CONTROLLERS.values());
	}
}
