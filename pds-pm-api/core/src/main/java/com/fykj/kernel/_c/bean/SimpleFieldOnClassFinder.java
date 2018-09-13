package com.fykj.kernel._c.bean;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.fykj.kernel._c._i.JFinder;
import com.fykj.kernel._c.meta.JDefaultFieldMeta;
import com.fykj.util.JClassUtils;

public class SimpleFieldOnClassFinder implements JFinder<List<JDefaultFieldMeta>> {
	
	private final Class<?> clazz;
	
	public SimpleFieldOnClassFinder(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
	public List<JDefaultFieldMeta> find() {
		
		List<JDefaultFieldMeta> columnMetas=new ArrayList<JDefaultFieldMeta>(); 
		List<Field> fields=JClassUtils.getFields(clazz, true);
		for(Field  field:fields){
			JDefaultFieldMeta columnMeta=new JDefaultFieldMeta();
			columnMeta.setAnnotations(field.getAnnotations());
			columnMeta.setClazz(clazz);
			columnMeta.setField(field);
			columnMeta.setFieldName(field.getName());
			columnMeta.setSetterMethodName(JClassUtils.getSetterMethodName(field));
			columnMeta.setGetterMethodName(JClassUtils.getGetterMethodName(field));
			columnMetas.add(columnMeta);
			
			Type type= field.getGenericType();
			if(ParameterizedType.class.isInstance(type)){
				columnMeta.setActualTypeArgument(((ParameterizedType)type).getActualTypeArguments());
			}
			
		}
		return columnMetas;
	}
	
}
