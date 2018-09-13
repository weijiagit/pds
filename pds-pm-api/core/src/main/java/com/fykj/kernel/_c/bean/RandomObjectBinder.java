package com.fykj.kernel._c.bean;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.fykj.kernel._c._random.JFieldRandom;
import com.fykj.kernel._c._random.JRandom;
import com.fykj.kernel._c._random.JTypeRandom;
import com.fykj.util.JClassUtils;

public class RandomObjectBinder<T> 
implements ClassFieldBinder ,SimpleObjectBinder<T> {

	protected FieldMatcher fieldMatcher=new FieldMatcher() {
		
		@Override
		public boolean matches(Field field, Class<?>scanClass) {
			return true;
		}
	};
	
	private SimpleObjectBinder<Object> class2Object=new _InnerObjectBinder();
	
	private class _InnerObjectBinder implements SimpleObjectBinder<Object>{
		@Override
		public Object createObject(Class<Object> content) {
			if(content.isArray()){
				return Array.newInstance(content.getComponentType(), 1);
			}else if(List.class.isAssignableFrom(content)){
				return new ArrayList<>();
			}else if(Map.class.isAssignableFrom(content)){
				return new HashMap<>();
			}else if(Set.class.isAssignableFrom(content)){
				return new HashSet<>();
			}else {
				return new JTypeRandom<>(content).random().random();
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void  setClass2Object(SimpleObjectBinder<?> class2Object) {
		this.class2Object = (SimpleObjectBinder<Object>) class2Object;
	}

	@Override
	public void setFieldMatcher(FieldMatcher fieldMatcher) {
		this.fieldMatcher=fieldMatcher;
	}

	@SuppressWarnings("unchecked")
	private <M> M _createObject(Class<M> clazz){
		return (M) class2Object.createObject((Class<Object>) clazz);
	}
	
	private int countRandom(){
		return new Random().nextInt(11);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T createObject(Class<T> content) {
		if(content.isArray()){
			Class<?> clazz=content.getComponentType();
			int count=countRandom();
			Object array=Array.newInstance(clazz, count);
			for(int i=0;i<count;i++){
				Object obj=createObject0((Class<T>) clazz);
				Array.set(array, i, obj);
			}
			return (T) array;
		}
		return createObject0(content);
	}

	@SuppressWarnings("unchecked")
	private T createObject0(Class<T> content) {
		T object=_createObject(content);
		SimpleFieldOnClassFinder classFinder=new SimpleFieldOnClassFinder(content);
		classFinder.find()
		.stream()
		.filter( fieldMeta ->{
			int modify=fieldMeta.getField().getModifiers();
			return (modify==Modifier.PRIVATE)
					||  (modify==Modifier.PROTECTED)
					;
		})
		.forEach(fieldMeta -> {
			if(fieldMatcher.matches(fieldMeta.getField(), fieldMeta.getClazz())){
				if(JClassUtils.isSimpleType(fieldMeta.getType())){
					JFieldRandom<Object> fieldRandom=new JFieldRandom<Object>(fieldMeta);
					Object val=fieldRandom.random();
					while(JRandom.class.isInstance(val)){
						val=((JRandom)val).random();
					}
					JClassUtils.setOnField(fieldMeta.getField(), val, object);
				}else{
					Object complex=createObject0((Class<T>) fieldMeta.getType());
					if(List.class.isInstance(complex)){
						List<? super Object> list= (List<? super Object>) complex;
						for(int i=0;i<countRandom();i++){
							list.add(createObject0((Class<T>) fieldMeta.getActualTypeArgument()[0]));
						}
					}else if(Map.class.isInstance(complex)){
						Map<? super Object, ? super Object> map=(Map<? super Object, ? super Object>) complex;
						for(int i=0;i<countRandom();i++){
							Object key=createObject0((Class<T>) fieldMeta.getActualTypeArgument()[0]);
							Object value=createObject0((Class<T>) fieldMeta.getActualTypeArgument()[1]);
							map.put(key, value);
						}
					}else if(Set.class.isInstance(complex)){
						Set<? super Object> set=(Set<? super Object>) complex;
						for(int i=0;i<countRandom();i++){
							set.add(createObject0((Class<T>) fieldMeta.getActualTypeArgument()[0]));
						}
					}
					JClassUtils.setOnField(fieldMeta.getField(), complex, object);
				}
			}
		});
		return object;
	}
	
}
