package com.fykj.kernel._c.bean;

public interface SimpleObjectBinder<T> extends ObjectBinder<Class<T>,T>  {
	
	@Override
	default public T createObject(Class<T> content) {
		try {
			return content.newInstance();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
}
